package org.springframework.samples.petclinic.service;

import java.time.LocalDate;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Beauty;
import org.springframework.samples.petclinic.model.Hability;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.model.Sponsor;
import org.springframework.samples.petclinic.repository.springdatajpa.HabilityRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedSponsorNameException;
import org.springframework.samples.petclinic.service.exceptions.JudgeNotFoundException;
import org.springframework.samples.petclinic.service.exceptions.ReservedDateExeception;
import org.springframework.samples.petclinic.service.exceptions.SponsorAmountException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class HabilityServiceTests {

	@Autowired
	protected HabilityService habilityService;

	@Autowired
	protected HabilityRepository habilityRepo;

	@Autowired
	SponsorService sponsorService;

	@Autowired
	JudgeService judgeService;

	@PersistenceContext
	protected EntityManager entityM;

	@ParameterizedTest
	@CsvSource({ "8000, Circuit Test, 2020-04-16, Hability ConTEST, 1000",
			"8000, Circuit Test, 2020-06-08, Hability ConTEST 2, 1000" })
	public void addNewHabilityContest(Integer capacity, String circuit, LocalDate date, String name,
			Double rewardMoney) {
		Hability hability = new Hability();
		hability.setCapacity(capacity);
		hability.setCircuit(circuit);
		hability.setDate(date);
		hability.setName(name);
		hability.setRewardMoney(rewardMoney);
		try {
			this.habilityService.saveHability(hability);
			assertThat(hability.getId()).isNotNull();
		} catch (ReservedDateExeception ex) {
			Logger.getLogger(HabilityServiceTests.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Test
	public void checkFirstPrize() {
		Hability hability = habilityRepo.findById(4).get();
		assertThat(hability.getFirstClassified()).isEqualTo(hability.getRewardMoney() * 0.5);
	}

	@Test
	public void checkSecondPrize() {
		Hability hability = habilityRepo.findById(4).get();
		assertThat(hability.getSecondClassified()).isEqualTo(hability.getRewardMoney() * 0.35);
	}

	@Test
	public void checkThirdPrize() {
		Hability hability = habilityRepo.findById(4).get();
		assertThat(hability.getThirdClassified()).isEqualTo(hability.getRewardMoney() * 0.15);
	}

	@Test
	public void editHabilityContestSuccess() throws ReservedDateExeception, SponsorAmountException, JudgeNotFoundException {
		Hability hability = this.habilityService.findHabilityById(4);
		hability.setCapacity(1000);
		hability.setDate(LocalDate.of(2042, 12, 2));
		this.habilityService.editHability(hability);
		assertThat(this.habilityService.findHabilityById(4).getCapacity()).isEqualTo(1000);
	}

	@Test
	public void editHabilityDateException() {
		Hability hability = this.habilityService.findHabilityById(4);
		hability.setDate(LocalDate.of(2020, 7, 12));
		String exeception = "";
		try {
			this.habilityService.editHability(hability);
		} catch (ReservedDateExeception ex) {
			Logger.getLogger(HabilityServiceTests.class.getName()).log(Level.SEVERE, null, ex);
			// Como editamos el la fecha a una que ya tiene asignado el Tourneo con ID 5,
			// salta esta excepcion
			exeception = ReservedDateExeception.class.getName();
			Logger.getLogger(HabilityServiceTests.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SponsorAmountException ex) {
			exeception = SponsorAmountException.class.getName();
			Logger.getLogger(HabilityServiceTests.class.getName()).log(Level.SEVERE, null, ex);
		} catch (JudgeNotFoundException ex) {
			exeception = JudgeNotFoundException.class.getName();
			Logger.getLogger(HabilityServiceTests.class.getName()).log(Level.SEVERE, null, ex);
		}
		// Comprobamos que salta la excepcion que estamos buscando
		assertThat(exeception).isEqualTo(ReservedDateExeception.class.getName());
	}

	@ParameterizedTest
	@CsvSource({ "PENDING", "FINISHED" })
	public void editHabilityWithSponsorsAndJudge(String status) throws ReservedDateExeception, SponsorAmountException,JudgeNotFoundException, DataAccessException, DuplicatedSponsorNameException {
		
		Hability hability = this.habilityService.findHabilityById(11);
		hability.setStatus(status);

		this.habilityService.editHability(hability);
		Hability habilityAssert = this.habilityService.findHabilityById(11);
		assertThat(habilityAssert.getSponsors().stream().mapToDouble(x -> x.getMoney()).sum())
				.isGreaterThanOrEqualTo(7000.00);
		assertThat(habilityAssert.getJudges()).isNotEmpty();
		assertThat(habilityAssert.getStatus()).isEqualTo(status);
	}

	@ParameterizedTest
	@CsvSource({ "0.", "6999.99" })
	public void editHabilitySponsorAmountException(Double money) throws DataAccessException, ReservedDateExeception, JudgeNotFoundException, DuplicatedSponsorNameException {
		String ex = "";

		Hability hability = this.habilityService.findHabilityById(8);

		Sponsor sponsor = new Sponsor();
		sponsor.setMoney(money);
		sponsor.setName("Testing");
		sponsor.setUrl("http://www.google.com");
		sponsor.setTournament(hability);

		hability.setStatus("PENDING");
		
		try {
			this.habilityService.editHability(hability);
		} catch (SponsorAmountException e) {
			Logger.getLogger(HabilityServiceTests.class.getName()).log(Level.SEVERE, null, e);
			ex = e.getClass().getName();
		}

		Assertions.assertThat(ex).isEqualTo(SponsorAmountException.class.getName());

	}

	@Test
	public void editHabilityJudgeException()
			throws ReservedDateExeception, SponsorAmountException, DataAccessException, DuplicatedSponsorNameException {
		Hability hability = this.habilityService.findHabilityById(4);
		hability.setStatus("PENDING");

		// creamos un sponsor que cumpla la condicion para cambiar el status (7.000,00 o más)
		List<Sponsor> sponsors = new ArrayList<Sponsor>();
		Sponsor sponsor = new Sponsor();
		sponsor.setName("Sponsor Test 2");
		sponsor.setMoney(7000.00);
		sponsors.add(sponsor);
		sponsor.setTournament(hability);
		sponsor.setUrl("https://www.google.com/");
		this.sponsorService.saveSponsor(sponsor);

		// asignamos el sponsor al torneo
		hability.setSponsors(sponsors);

		try {
			this.habilityService.editHability(hability);
		} catch (JudgeNotFoundException ex) {
			Logger.getLogger(HabilityServiceTests.class.getName()).log(Level.SEVERE, null, ex);
			Hability habilityAssert = this.habilityService.findHabilityById(4);
			assertThat(habilityAssert.getJudges()).isEmpty();
		}
	}
}