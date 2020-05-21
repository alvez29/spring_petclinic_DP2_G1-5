package org.springframework.samples.petclinic.service;


import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.model.locationiqapi.Place;
import org.springframework.samples.petclinic.repository.springdatajpa.ResultScoreRepository;
import org.springframework.samples.petclinic.repository.springdatajpa.ResultTimeRepository;
import org.springframework.samples.petclinic.repository.springdatajpa.TournamentRepository;
import org.springframework.stereotype.Service;

@Service
public class TournamentService {

	@Autowired
	private TournamentRepository tournamentRepo;
	
	@Autowired
	private ResultScoreRepository resultScoreRepo;

	@Autowired
	private ResultTimeRepository resultTimeRepo;
	
	@Transactional
	public int tournamentCount() {
		return (int) tournamentRepo.count();
	}
	
	@Transactional
	public Iterable<Tournament> findAll(){
		return tournamentRepo.findAll();
	}
	
	@Transactional
	@Cacheable("getPlace")
	public Place[] getPlace(String text) throws UnsupportedEncodingException {
		Place[] res = LocationIQAPIService.getPlace(text);
		if(res == null) {
			Place[] empty = {};
			res = empty;
		}
		
		return res;
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

	public Integer[] petHasResult(int tournamentId) {
		Tournament tournament = this.tournamentRepo.findById(tournamentId).get();
		List<Pet> pets = tournament.getPets();
		Integer maxId = 0;
		if(!pets.isEmpty()) {
			maxId = pets.stream().mapToInt(x->x.getId()).max().getAsInt();
		}
		Integer[] res = new Integer[maxId+1];

		if(getTournamentType(tournamentId).equals("Beauty")) {
			List<Pet> petWithResult = this.resultScoreRepo.findPetsWithResultByTournamentId(tournamentId);
			for(Pet p : pets) {
				if(petWithResult.contains(p)) {
					res[p.getId()] = 1;
				} else {
					res[p.getId()] = 0;
				}
			}
		} else {
			List<Pet> petWithResult = this.resultTimeRepo.findPetsWithResultByTournamentId(tournamentId);
			for(Pet p : pets) {
				if(petWithResult.contains(p)) {
					res[p.getId()] = 1;
				} else {
					res[p.getId()] = 0;

				}
			}
		}
				return res;
	}
}
