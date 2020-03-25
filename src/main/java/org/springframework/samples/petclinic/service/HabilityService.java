
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Hability;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Sponsor;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.springdatajpa.HabilityRepository;
import org.springframework.samples.petclinic.service.exceptions.ReservedDateExeception;
import org.springframework.samples.petclinic.service.exceptions.SponsorAmountException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HabilityService {

	private HabilityRepository	habilityRepo;

	private PetRepository		petRepo;

	@Autowired
	public HabilityService(final HabilityRepository habilityRepository, final PetRepository petRepository) {
		this.petRepo = petRepository;
		this.habilityRepo = habilityRepository;
	}

	@Transactional(rollbackFor = ReservedDateExeception.class)
	public void saveHability(@Valid final Hability hability) throws DataAccessException, ReservedDateExeception {
		if (fechaReservada(hability.getDate())) {
			throw new ReservedDateExeception();
		} else {
			this.habilityRepo.save(hability);
		}
	}
	
	@Transactional(rollbackFor = {ReservedDateExeception.class, SponsorAmountException.class})
	public void editHability(@Valid Hability hability) throws ReservedDateExeception, SponsorAmountException {
		if(getSponsorAmount(hability.getId()) < 7000.00 && (hability.getStatus() =="PENDING" || hability.getStatus() =="FINISHED")) {
			throw new SponsorAmountException();
		}
		
		if (fechaReservadaEdit(hability.getId(), hability.getDate())) {
			throw new ReservedDateExeception();
		} else {
			this.habilityRepo.save(hability);
		}
	}
	
	private boolean fechaReservadaEdit(Integer id, LocalDate date) {
		List<String> fechas = this.habilityRepo.getFutureDates();
		LocalDate miFecha = habilityRepo.getDateByHanilityId(id);
		if(fechas.contains(miFecha.toString())) {
			fechas.remove(miFecha.toString());
		}
		List<LocalDate> fechasLD = this.fechasString2LocalDate(fechas);
		Boolean res = fechasLD.contains(date);
		return res;
	}

	private boolean fechaReservada(final LocalDate date) {
		List<String> fechas = this.habilityRepo.getFutureDates();
		List<LocalDate> fechasLD = this.fechasString2LocalDate(fechas);
		Boolean res = fechasLD.contains(date);
		return res;
	}

	private List<LocalDate> fechasString2LocalDate(final List<String> fechas) {
		Integer i = 0;
		List<LocalDate> res = new ArrayList<LocalDate>();
		while (i <= fechas.size() - 1) {
			res.add(LocalDate.parse(fechas.get(i), DateTimeFormatter.ISO_LOCAL_DATE));
			i++;
		}
		return res;
	}

	@Transactional
	public List<PetType> getAllTypes() {
		return this.petRepo.findPetTypes();
	}
	
	@Transactional
	public Hability findHabilityById(int habilityId) {
		return this.habilityRepo.findById(habilityId).get();
	}

	public Double getSponsorAmount(int id) {
		Collection<Sponsor> res = this.habilityRepo.getSponsors(id);
		Double sum = 0.;
		if(!res.equals(null)) {
			sum = res.stream().mapToDouble(x->x.getMoney()).sum();
		}
		return sum;
	}

}
