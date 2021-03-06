package org.springframework.samples.petclinic.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.repository.springdatajpa.JudgeRepository;
import org.springframework.stereotype.Service;

@Service
public class JudgeService {

	@Autowired
	private JudgeRepository judgeRepo;
	
	@Autowired
	public JudgeService(JudgeRepository judgeRepo) {
		this.judgeRepo = judgeRepo;
	}		

	public Iterable<Judge> findAll() throws DataAccessException {
		return judgeRepo.findAll();
	}

	public Judge findJudgeById(int judgeId) {
		return judgeRepo.findById(judgeId).get();
	}

	public void saveJudge(@Valid Judge judge) {
		judgeRepo.save(judge);
	}	
}