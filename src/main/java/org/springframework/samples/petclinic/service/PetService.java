/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.ResultScore;
import org.springframework.samples.petclinic.model.ResultTime;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.samples.petclinic.repository.springdatajpa.TournamentRepository;
import org.springframework.samples.petclinic.service.exceptions.ClinicNotAuthorisedException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class PetService {

	private PetRepository petRepository;
	
	private VisitRepository visitRepository;
	
	@Autowired
	private TournamentRepository tournamnetRepository;
	
	@Autowired
	private RaceResultService raceResultService;
	
	@Autowired
	private BeautyResultService beautyResultService;
	

	@Autowired
	public PetService(PetRepository petRepository,
			VisitRepository visitRepository) {
		this.petRepository = petRepository;
		this.visitRepository = visitRepository;
	}

	@Transactional(readOnly = true)
	public Collection<PetType> findPetTypes() throws DataAccessException {
		return petRepository.findPetTypes();
	}
	
	@Transactional(rollbackFor = ClinicNotAuthorisedException.class)
	public void saveVisit(Visit visit) throws DataAccessException, ClinicNotAuthorisedException {
		List<String> authorisedClinics = new ArrayList<String>();
		authorisedClinics.add("Boyton Vet");
		authorisedClinics.add("Canin Vet");
		authorisedClinics.add("Sunrise Pet Clinic");
		authorisedClinics.add("Pet Health Center");
		authorisedClinics.add("Midlands Veterinary");
		authorisedClinics.add("Frankford Animal Clinic");
		authorisedClinics.add("Dog And Cat Hospital");
		
		Boolean isNotCheck = visit.getCompetitionCheck().contentEquals("-");
		if(isNotCheck || authorisedClinics.contains(visit.getClinic())) {
			visitRepository.save(visit);
		} else {
			throw new ClinicNotAuthorisedException();
		}
		
	}

	@Transactional(readOnly = true)
	public Pet findPetById(int id) throws DataAccessException {
		return petRepository.findById(id);
	}

	public Iterable<Pet> findAll() throws DataAccessException {
		return petRepository.findAll();
	}
	
	
	@Transactional(rollbackFor = DuplicatedPetNameException.class)
	public void savePet(Pet pet) throws DataAccessException, DuplicatedPetNameException {
			Pet otherPet = pet.getOwner().getPetwithIdDifferent(pet.getName(), pet.getId());
            if (StringUtils.hasLength(pet.getName()) &&  (otherPet!= null && otherPet.getId()!=pet.getId())) {            	
            	throw new DuplicatedPetNameException();
            }else
                petRepository.save(pet);                
	}


	public Collection<Visit> findVisitsByPetId(int petId) {
		return visitRepository.findByPetId(petId);
	}
	
	public boolean isActualWinner(Integer petId) {
		boolean res = false;
		List<Tournament> tournaments = this.tournamnetRepository.findTounamentsByPetId(petId);
		List<Tournament> tournamentsInAYear = findTournamentInAYear(tournaments);
		if (!tournamentsInAYear.equals(null)) {
			for (Tournament t : tournamentsInAYear) {				
				String canodrome = this.tournamnetRepository.getCanodrome(t.getId());
				String place = this.tournamnetRepository.getPlace(t.getId());				
				if (canodrome!=null) {					
					res = isPetWinnerRace(t, petId);					
				} else if (place!=null) {
					res = isPetWinnerBeauty(t, petId);
				} else {
					res = isPetWinnerHability(t, petId);
				}
				if (res) {
					break;
				}
			}
		}
		return res;
	}
	
	private boolean isPetWinnerRace(Tournament t, Integer petId) {
		boolean res = false;
		List<ResultTime> results = raceResultService.findByTournamnetId(t.getId());
		Collections.sort(results, (x, y) -> x.getTime().compareTo(y.getTime()));
		List<Integer> winners1T = results.stream().map(x -> x.getPet().getId())
				.collect(Collectors.toList());
		if(winners1T.size()<=3) {
			res = true;
		} else {
			res = winners1T.subList(0, 3).contains(petId);

		}
		return res;
	}
	
	private boolean isPetWinnerBeauty(Tournament t, Integer petId) {	
		boolean res = false;
		List<ResultScore> resultS = beautyResultService.findByTournamentId(t.getId());
		Collections.sort(resultS, (x, y) -> x.getTotalPoints().compareTo(y.getTotalPoints()));
		List<Integer> winners1T = resultS.stream().map(x -> x.getPet().getId())
				.collect(Collectors.toList());
		if(winners1T.size()<=3) {
			res = true;
		} else {
			res = winners1T.subList(0, 3).contains(petId);

		}
		return res;
	}
	
	private boolean isPetWinnerHability(Tournament t, Integer petId) {
		boolean res = false;
		List<ResultTime> results = raceResultService.findByTournamnetId(t.getId());
		Collections.sort(results, (x, y) -> x.getTotalResult().compareTo(y.getTotalResult()));
		List<Integer> winners1T = results.stream().map(x -> x.getPet().getId())
				.collect(Collectors.toList());
		if(winners1T.size()<=3) {
			res = true;
		} else {
			res = winners1T.subList(0, 3).contains(petId);
		}
		return res;

	}

	
	private List<Tournament> findTournamentInAYear(List<Tournament> tournaments) {
		List<Tournament> res = tournaments.stream()
				.filter(x->LocalDate.now().isBefore(x.getDate().plusYears(1)))
				.filter(x->LocalDate.now().isAfter(x.getDate()))
				.collect(Collectors.toList());
		return res;
	}

}
