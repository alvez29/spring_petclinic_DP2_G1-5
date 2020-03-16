package org.springframework.samples.petclinic.service;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Race;
import org.springframework.samples.petclinic.repository.springdatajpa.RaceRepository;
import org.springframework.samples.petclinic.service.exceptions.ReservedDateExeception;
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
	public void checkPrizes() {
		Race race = this.raceRepo.findById(1).get();
		
		assertThat(race.getFirstClassified()).isEqualTo(race.getRewardMoney()*0.5);
		assertThat(race.getSecondClassified()).isEqualTo(race.getRewardMoney()*0.35);
		assertThat(race.getThirdClassified()).isEqualTo(race.getRewardMoney()*0.15);
	}
}
