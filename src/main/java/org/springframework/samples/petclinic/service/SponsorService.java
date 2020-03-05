package org.springframework.samples.petclinic.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.repository.springdatajpa.SponsorRepository;
import org.springframework.stereotype.Service;

@Service
public class SponsorService {

	@Autowired
	private SponsorRepository sponsorRepo;
	
	@Transactional
	public int sponsorCount() {
		return (int) sponsorRepo.count();
	}
}
