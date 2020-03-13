package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.repository.JudgeRepository;
import org.springframework.stereotype.Service;

@Service
public class JudgeService {

	@Autowired
	private JudgeRepository judgeRepo;
	
	@Autowired
	public JudgeService(JudgeRepository judgeRepo) {
		this.judgeRepo = judgeRepo;
	}		

	public Collection<Judge> findVets() throws DataAccessException {
		return judgeRepo.findAll();
	}	
}
