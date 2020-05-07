package org.springframework.samples.petclinic.service;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.model.Race;
import org.springframework.samples.petclinic.model.Sponsor;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedSponsorNameException;
import org.springframework.samples.petclinic.service.exceptions.JudgeNotFoundException;
import org.springframework.samples.petclinic.service.exceptions.ReservedDateExeception;
import org.springframework.samples.petclinic.service.exceptions.SponsorAmountException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class RaceServiceTests {
	
	@Autowired
	protected RaceService raceService;
	
	@Autowired
	protected JudgeService judgeService;
	
	@Autowired
	protected SponsorService sponsorService;

	
	@ParameterizedTest
	@CsvSource({"8000, Canodrome Test, 2020-04-16, 1000, Race 1 Test", "8000, Canodrome Test, 2020-06-08, 1000, Race 2 Test"})
	public void addNewRace(Integer capacity, String canodrome, LocalDate date,
			Double rewardMoney, String name) {
	
		Race race = new Race();
		
		race.setCapacity(capacity);
		race.setCanodrome(canodrome);
		race.setDate(date);
		race.setRewardMoney(rewardMoney);
		race.setName(name);
		
		try{
			this.raceService.saveRace(race);
			assertThat(race.getId()).isNotNull();
			
		}catch(ReservedDateExeception e){
			Logger.getLogger(RaceServiceTests.class.getName()).log(Level.SEVERE, null, e);
	        assertThat(race.getId()).isNull();
		}

	}
	
	
	@Test
	public void checkFirstClassified() {
		Race race = this.raceService.findRaceById(1);
		
		assertThat(race.getFirstClassified()).isEqualTo(race.getRewardMoney()*0.5);	
	}
	
	
	@Test
	public void checkSecondClassified() {
		Race race = this.raceService.findRaceById(1);
		
		assertThat(race.getSecondClassified()).isEqualTo(race.getRewardMoney()*0.35);		
	}
	
	@Test
	public void checkThirdClassified() {
		Race race = this.raceService.findRaceById(1);
		
		assertThat(race.getThirdClassified()).isEqualTo(race.getRewardMoney()*0.15);
			
	}
	
	@Test
	public void editRaceSuccess() throws DataAccessException, SponsorAmountException, ReservedDateExeception, JudgeNotFoundException {
		
		Race race = this.raceService.findRaceById(1);
		race.setCapacity(8000);
//		race.setCanodrome("Gran Hipodromo de Andalucia");
		race.setDate(LocalDate.of(2040, 04, 16));
		race.setRewardMoney(1000.);
		race.setName("Race 1 Test");
		
		this.raceService.editRace(race);
		assertThat(race.getId()).isNotNull();
		Race editedRace = this.raceService.findRaceById(1);
		assertThat(editedRace.getCapacity()).isEqualTo(race.getCapacity());
//		assertThat(editedRace.getCanodrome()).isEqualTo(race.getCanodrome());
			
		

	}
	
	@Test
	public void editRaceReservedDateException() throws DataAccessException, SponsorAmountException, JudgeNotFoundException {
	
		String ex = "";
	
		Race race = this.raceService.findRaceById(2);
		race.setDate(LocalDate.of(2020, 7, 12));
		race.setCapacity(1);
		
		try {
			this.raceService.editRace(race);
		}catch(ReservedDateExeception e) {
			Logger.getLogger(PetServiceTests.class.getName()).log(Level.SEVERE, null, e);
			ex = e.getClass().getName();
		}
		
		 assertThat(ex).isEqualTo(ReservedDateExeception.class.getName());
		
	}
	
	@ParameterizedTest
	@CsvSource({"0.","6999.99"})
	public void editRaceSponsorAmountException(Double money) throws DataAccessException, SponsorAmountException, ReservedDateExeception, JudgeNotFoundException {
		String ex = "";
		
		Race race = this.raceService.findRaceById(2);
		
		Sponsor sponsor = new Sponsor();
		sponsor.setMoney(money);
		sponsor.setName("Testing");
		sponsor.setUrl("http://www.google.com");
		sponsor.setTournament(race);

		race.setStatus("PENDING");
		
		try {
			this.raceService.editRace(race);
		}catch(SponsorAmountException e) {
			Logger.getLogger(PetServiceTests.class.getName()).log(Level.SEVERE, null, e);
			ex = e.getClass().getName();
		}
		
		 assertThat(ex).isEqualTo(SponsorAmountException.class.getName());
		
	}
	
	
	@ParameterizedTest
	@CsvSource({"PENDING", "FINISHED"})
	public void editRaceWithSponsor(String status) throws DataAccessException, SponsorAmountException, ReservedDateExeception, JudgeNotFoundException, DuplicatedSponsorNameException {
		
		Race race = this.raceService.findRaceById(2);
		race.setStatus(status);	
		
		Sponsor sponsor = new Sponsor();
		sponsor.setMoney(7000.);
		sponsor.setName("Testing");
		sponsor.setUrl("http://www.google.com");
		sponsor.setTournament(race);

		this.sponsorService.saveSponsor(sponsor);
		
		List<Sponsor> sponsorList = new ArrayList<Sponsor>();
		sponsorList.add(sponsor);
		
		race.setSponsors(sponsorList);
		
		
		try {
			this.raceService.editRace(race);
		}catch(SponsorAmountException e) {
			Logger.getLogger(PetServiceTests.class.getName()).log(Level.SEVERE, null, e);
		}
		
		Race newRace = this.raceService.findRaceById(2);
		
		assertThat(newRace.getSponsors().stream().mapToDouble(x->x.getMoney()).sum()).isGreaterThanOrEqualTo(7000.);
		assertThat(newRace.getJudges()).isNotEmpty();
		assertThat(newRace.getStatus()).isEqualTo(status);		
	}

	
	@ParameterizedTest
	@CsvSource({"PENDING", "FINISHED"})
	public void editRaceWithoutJudge(String status) throws DataAccessException, SponsorAmountException, ReservedDateExeception, JudgeNotFoundException, DuplicatedSponsorNameException {
		String ex = "";
		
		Race race = this.raceService.findRaceById(2);
		race.setStatus(status);	
		
		Sponsor sponsor = new Sponsor();
		sponsor.setMoney(7000.);
		sponsor.setName("Testing");
		sponsor.setUrl("http://www.google.com");
		sponsor.setTournament(race);

		this.sponsorService.saveSponsor(sponsor);
		
		List<Sponsor> sponsorList = new ArrayList<Sponsor>();
		sponsorList.add(sponsor);
		
		race.setSponsors(sponsorList);
		
		List<Judge> judgeList = new ArrayList<Judge>();
		race.setJudges(judgeList);

		try {
			this.raceService.editRace(race);
		}catch(JudgeNotFoundException e) {
			Logger.getLogger(PetServiceTests.class.getName()).log(Level.SEVERE, null, e);
			ex = e.getClass().getName();
		}		
		assertThat(ex).isEqualTo(JudgeNotFoundException.class.getName());	
	}

	
	
	

}
