package org.springframework.samples.petclinic.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Beauty;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.springdatajpa.BeautyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BeautyService {
	
	private BeautyRepository beautyRepo;
	
	private PetRepository petRepo;
	
	@Autowired
	public BeautyService(BeautyRepository beautyRepository, PetRepository petRepository) {
		this.petRepo = petRepository;
		this.beautyRepo = beautyRepository;	
	}
	
	@Transactional
	public void saveBeauty(@Valid Beauty beauty) {
		beautyRepo.save(beauty);
	}
	
	@Transactional
	public List<PetType> getAllTypes(){
		return petRepo.findPetTypes();
	}

}
