package org.springframework.samples.petclinic.service;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
	
	@Transactional
	public Tournament findTournamentById(int id) throws DataAccessException {
		return tournamentRepo.findById(id).get();
	}
	
	public void saveTournament(Tournament tournament) {
		tournamentRepo.save(tournament);
	}
	
	@Transactional
	public String getTournamentType(int tournamentId) {
		return tournamentRepo.findTournamentType(tournamentId);
	}
	
	@Transactional
	public String getSite(int tournamentId) {
		String type = getTournamentType(tournamentId);
		String res = "";
		
		if(type.equals("Race")) {
			res = tournamentRepo.getCanodrome(tournamentId);
		}else if(type.equals("Hability")) {
			res = tournamentRepo.getCircuit(tournamentId);
		}else if(type.equals("Beauty")) {
			res = tournamentRepo.getPlace(tournamentId);
		}
		
		return res;
	}
	
}
