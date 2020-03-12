package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.format.datetime.joda.DateTimeFormatterFactory;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Race;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.springdatajpa.RaceRepository;
import org.springframework.samples.petclinic.service.exceptions.ReservedDateExeception;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RaceService {
	
	private RaceRepository raceRepo;
	
	private PetRepository petRepo;
	
	@Autowired
	public RaceService(RaceRepository raceRepository, PetRepository petRepository) {
		this.petRepo = petRepository;
		this.raceRepo = raceRepository;
	}
	
	private Boolean fechaReservada(LocalDate fecha) {
		List<String> fechas = raceRepo.getFutureDates();
		List<LocalDate> fechasLD = fechasString2LocalDate(fechas);
		Boolean res = fechasLD.contains(fecha);
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

	@Transactional(rollbackFor = ReservedDateExeception.class)
	public void saveRace(@Valid Race race) throws DataAccessException, ReservedDateExeception {
		if(fechaReservada(race.getDate())) {
			throw new ReservedDateExeception();
		} else {
			raceRepo.save(race);
		}
	}

	@Transactional
	public List<PetType> getAllTypes(){
		return petRepo.findPetTypes();
		
	}

}
