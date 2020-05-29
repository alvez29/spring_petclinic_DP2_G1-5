
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Beauty;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Sponsor;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.springdatajpa.BeautyRepository;
import org.springframework.samples.petclinic.service.exceptions.JudgeNotFoundException;
import org.springframework.samples.petclinic.service.exceptions.ReservedDateExeception;
import org.springframework.samples.petclinic.service.exceptions.SponsorAmountException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BeautyService {

	private BeautyRepository	beautyRepo;

	private PetRepository		petRepo;


	@Autowired
	public BeautyService(final BeautyRepository beautyRepository, final PetRepository petRepository) {
		this.petRepo = petRepository;
		this.beautyRepo = beautyRepository;
	}

	@Transactional(rollbackFor = ReservedDateExeception.class)
	public void saveBeauty(@Valid final Beauty beauty) throws DataAccessException, ReservedDateExeception {
		if (this.fechaReservada(beauty.getDate())) {
			throw new ReservedDateExeception();
		} else {
			this.beautyRepo.save(beauty);
		}
	}

	private boolean fechaReservada(final LocalDate date) {
		List<String> fechas = this.beautyRepo.getFutureDates();
		List<LocalDate> fechasLD = this.fechasString2LocalDate(fechas);
		return fechasLD.contains(date);
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

	public Beauty findBeautyById(final int beautyId) {
		return this.beautyRepo.findById(beautyId).get();
	}

	public Collection<Sponsor> getSponsors(final int beautyId) {
		return this.beautyRepo.getSponsors(beautyId);
	}

	@Transactional(rollbackFor = {
		ReservedDateExeception.class, SponsorAmountException.class, JudgeNotFoundException.class
	})
	public void editBeauty(@Valid final Beauty beauty) throws ReservedDateExeception, SponsorAmountException, JudgeNotFoundException {

		if (this.getSponsorAmount(beauty.getId()) < 7000.00 && (beauty.getStatus().equals("PENDING") || beauty.getStatus().equals("FINISHED"))) {
			throw new SponsorAmountException();
		}

		if ((beauty.getStatus().equals("PENDING") || beauty.getStatus().equals("FINISHED")) && this.beautyRepo.getJudgeById(beauty.getId()).isEmpty()) {
			throw new JudgeNotFoundException();
		}

		if (this.fechaReservadaEdit(beauty.getId(), beauty.getDate())) {
			throw new ReservedDateExeception();
		} else {

			this.beautyRepo.save(beauty);
		}
	}

	public Double getSponsorAmount(final int id) {
		Collection<Sponsor> res = this.beautyRepo.getSponsors(id);
		Double sum = 0.;
		if (!res.equals(null)) {
			sum = res.stream().mapToDouble(x -> x.getMoney()).sum();
		}
		return sum;
	}

	private boolean fechaReservadaEdit(final Integer id, final LocalDate date) {
		List<String> fechas = this.beautyRepo.getFutureDates();
		LocalDate miFecha = this.beautyRepo.getDateByBeautyId(id);
		if (fechas.contains(miFecha.toString())) {
			fechas.remove(miFecha.toString());
		}
		List<LocalDate> fechasLD = this.fechasString2LocalDate(fechas);
		Boolean res = fechasLD.contains(date);
		return res;
	}

}
