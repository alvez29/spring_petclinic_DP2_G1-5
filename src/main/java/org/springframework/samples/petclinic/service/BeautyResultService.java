package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.ResultScore;
import org.springframework.samples.petclinic.repository.springdatajpa.ResultScoreRepository;
import org.springframework.stereotype.Service;

@Service
public class BeautyResultService {

	@Autowired
	private ResultScoreRepository resultScoreRepo;


	public List<ResultScore> findByTournamentId(int id) {
		return resultScoreRepo.findByTournamentId(id);
	}
	
}
