package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.ResultTime;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.repository.springdatajpa.ResultTimeRepository;
import org.springframework.samples.petclinic.repository.springdatajpa.TournamentRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedResultForPetInTournament;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RaceResultService {
	
	@Autowired
	private ResultTimeRepository resultTimeRepo;
	
	@Autowired
	private TournamentRepository tournamentRepo;

	@Autowired
	public RaceResultService(ResultTimeRepository resultTimeRepo2, TournamentRepository tournamentRepo2) {
		this.resultTimeRepo = resultTimeRepo2;
		this.tournamentRepo = tournamentRepo2;
	}


	public Iterable<ResultTime> findAll() {
		return resultTimeRepo.findAll();
	}

	public List<ResultTime> findByTournamnetId(int tournamnetId) {
		return resultTimeRepo.findByTournamentId(tournamnetId);
	}

	@Transactional(rollbackFor = DuplicatedResultForPetInTournament.class)
	public void saveResult(@Valid ResultTime resultTime) throws DuplicatedResultForPetInTournament {

		Integer petId = resultTime.getPet().getId();
		Integer tournamentId = resultTime.getTournament().getId();
		
		if(this.resultTimeRepo.hasResult(petId,tournamentId) != 0) {
			throw new DuplicatedResultForPetInTournament();
		}else {
			resultTimeRepo.save(resultTime);
		}
	}

	public boolean isInTournament(int tournamentId, int petId) {
		Tournament tourn = this.tournamentRepo.findById(tournamentId).get();
		List<Pet> participants = tourn.getPets();
		List<Integer> participantsIds = participants.stream()
													.map(x->x.getId())
													.collect(Collectors.toList());	
		return participantsIds.contains(petId);
	} 

}
