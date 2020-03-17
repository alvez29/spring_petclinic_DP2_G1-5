package org.springframework.samples.petclinic.service;


import java.time.LocalDate;

import java.util.logging.Logger;
import java.util.logging.Level;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Hability;
import org.springframework.samples.petclinic.repository.springdatajpa.HabilityRepository;
import org.springframework.samples.petclinic.service.exceptions.ReservedDateExeception;
import org.springframework.stereotype.Service;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class HabilityServiceTests {
	
	@Autowired
	protected HabilityService habilityService;
	
	@Autowired
	protected HabilityRepository habilityRepo;

	@ParameterizedTest
    @CsvSource({"8000, Circuit Test, 2020-04-16, Hability ConTEST, 1000", "8000, Circuit Test, 2020-06-08, Hability ConTEST 2, 1000"})
	public void addNewHabilityContest(Integer capacity, String circuit, LocalDate date, String name, Double rewardMoney) {
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
		assertThat(hability.getFirstClassified()).isEqualTo(hability.getRewardMoney()*0.5);
	}
	
	@Test
	public void checkSecondPrize() {
		Hability hability = habilityRepo.findById(4).get();
		assertThat(hability.getSecondClassified()).isEqualTo(hability.getRewardMoney()*0.35);	
	}
	
	@Test
	public void checkThridPrize() {
		Hability hability = habilityRepo.findById(4).get();
		assertThat(hability.getThirdClassified()).isEqualTo(hability.getRewardMoney()*0.15);
	}

}
