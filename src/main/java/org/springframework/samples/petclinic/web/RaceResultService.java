package org.springframework.samples.petclinic.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.ResultTime;
import org.springframework.samples.petclinic.repository.springdatajpa.ResultTimeRepository;
import org.springframework.stereotype.Service;

@Service
public class RaceResultService {
	
	@Autowired
	private ResultTimeRepository resultTimeRepo;

	public Iterable<ResultTime> findAll() {
		return resultTimeRepo.findAll();
	}

	public List<ResultTime> findByTournamnetId(int tournamnetId) {
		return resultTimeRepo.findByTournamentId(tournamnetId);
	} 

}
