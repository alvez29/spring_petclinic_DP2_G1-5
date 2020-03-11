package org.springframework.samples.petclinic.service;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Race;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.springdatajpa.RaceRepository;
import org.springframework.stereotype.Service;

@Service
public class RaceService {
	
	private RaceRepository raceRepo;
	
	private PetRepository petRepo;
	
	@Autowired
	public RaceService(RaceRepository raceRepository, PetRepository petRepository) {
		this.petRepo = petRepository;
		this.raceRepo = raceRepository;
	}

	@Transactional
	public void saveRace(@Valid Race race) {
		raceRepo.save(race);
	}

	@Transactional
	public List<PetType> getAllTypes(){
		return petRepo.findPetTypes();
		
	}
}
