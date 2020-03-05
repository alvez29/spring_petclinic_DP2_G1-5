package org.springframework.samples.petclinic.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.repository.springdatajpa.TournamentRepository;
import org.springframework.stereotype.Service;

@Service
public class TournamentService {

	@Autowired
	private TournamentRepository tournamentRepo;
	
	@Transactional
	public int tournamentCount() {
		return (int) tournamentRepo.count();
	}
	
	@Transactional
	public Iterable<Tournament> findAll(){
		return tournamentRepo.findAll();
	}
}
