package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Beauty;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.springdatajpa.BeautyRepository;
import org.springframework.samples.petclinic.service.exceptions.ReservedDateExeception;
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
	
	@Transactional(rollbackFor = ReservedDateExeception.class)
	public void saveBeauty(@Valid Beauty beauty) throws DataAccessException, ReservedDateExeception {
		if(fechaReservada(beauty.getDate())) {
			throw new ReservedDateExeception();
		}else {
			beautyRepo.save(beauty);
		}
	}
	
	private boolean fechaReservada(LocalDate date) {
		List<String> fechas = beautyRepo.getFutureDates();
		List<LocalDate> fechasLD = fechasString2LocalDate(fechas);
		Boolean res = fechasLD.contains(date);
		return res;
	}

	private List<LocalDate> fechasString2LocalDate(List<String> fechas) {
		Integer i = 0;
		List<LocalDate> res = new ArrayList<LocalDate>();
		while(i <= fechas.size() -1) {
			res.add(LocalDate.parse(fechas.get(i), DateTimeFormatter.ISO_LOCAL_DATE));
			i++;
		}
		return res;
	}
	
	@Transactional
	public List<PetType> getAllTypes(){
		return petRepo.findPetTypes();
	}

}
