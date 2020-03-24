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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Race;
import org.springframework.samples.petclinic.model.Sponsor;
import org.springframework.samples.petclinic.repository.springdatajpa.RaceRepository;
import org.springframework.samples.petclinic.service.exceptions.ReservedDateExeception;
import org.springframework.samples.petclinic.service.exceptions.SponsorAmountException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class RaceServiceTests {
	
	@Autowired
	protected RaceService raceService;
	
	@Autowired
	protected RaceRepository raceRepo;
	
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
			Logger.getLogger(PetServiceTests.class.getName()).log(Level.SEVERE, null, e);
	        assertThat(race.getId()).isNull();
		}

	}
	
	
	@Test
	public void checkFirstClassified() {
		Race race = this.raceRepo.findById(1).get();
		
		assertThat(race.getFirstClassified()).isEqualTo(race.getRewardMoney()*0.5);	
	}
	
	
	@Test
	public void checkSecondClassified() {
		Race race = this.raceRepo.findById(1).get();
		
		assertThat(race.getSecondClassified()).isEqualTo(race.getRewardMoney()*0.35);		
	}
	
	@Test
	public void checkThirdClassified() {
		Race race = this.raceRepo.findById(1).get();
		
		assertThat(race.getThirdClassified()).isEqualTo(race.getRewardMoney()*0.15);
			
	}
	
	@ParameterizedTest
	@CsvSource({"8000, Canodrome Test, 2020-04-16, 1000, Race 1 Test, DRAFT", "8000, Canodrome Test, 2020-06-08, 1000, Race 2 Test, DRAFT","8000, Canodrome Test, 2020-08-11, 1000, Race 3 Test, PENDING"})
	public void editRace(Integer capacity, String canodrome, LocalDate date,
		Double rewardMoney, String name, String status) throws DataAccessException, SponsorAmountException {
		
		Race race = new Race();
		List<Sponsor> sponsors = new ArrayList<Sponsor>();
		race.setId(1);
		race.setCapacity(capacity);
		race.setCanodrome(canodrome);
		race.setDate(date);
		race.setRewardMoney(rewardMoney);
		race.setName(name);
		race.setStatus(status);
		race.setSponsors(sponsors);
		
		try{
			this.raceService.editRace(race);
			assertThat(race.getId()).isNotNull();
			
		}catch(ReservedDateExeception|SponsorAmountException e){
			Logger.getLogger(PetServiceTests.class.getName()).log(Level.SEVERE, null, e);
	        assertThat(race.getId()).isNull();
		}

	}
	
	
	@ParameterizedTest
	@CsvSource({"PENDING", "FINISHED"})
	public void editRaceWithSponsor(String status) throws DataAccessException, SponsorAmountException, ReservedDateExeception {
		
		Race race = new Race();
		
		Sponsor s1 = new Sponsor();
		s1.setMoney(7000.);
		s1.setName("Test");
		
		List<Sponsor> sponsors = new ArrayList<Sponsor>();
		sponsors.add(s1);
		race.setId(1);
		race.setCapacity(8000);
		race.setCanodrome("Canodrome Test");
		race.setDate(LocalDate.now().plusMonths(1));
		race.setRewardMoney(1000.);
		race.setName("Race Test 4");
		race.setStatus(status);
		race.setSponsors(sponsors);
		

		this.raceService.editRace(race);
		assertThat(race.getId()).isNotNull();
		Double sum = race.getSponsors().stream().mapToDouble(x->x.getMoney()).sum();
		assertThat(sum).isGreaterThanOrEqualTo(7000.);
	

	}

	
	
	
	

}
