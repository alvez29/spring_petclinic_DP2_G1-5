package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Beauty;
import org.springframework.samples.petclinic.repository.springdatajpa.BeautyRepository;
import org.springframework.samples.petclinic.service.exceptions.ReservedDateExeception;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class BeautyServiceTests {

	@Autowired
	protected BeautyService		beautyService;

	@Autowired
	protected BeautyRepository	beautyRepo;


	@ParameterizedTest
	@CsvSource({
		"8000,PLaceTest,2020-04-16,BeautyConTest2,1000", "8000,PLaceTest,2020-06-08,BeautyConTest,1000"
	})
	public void addNewBeauty(final Integer capacity, final String place, final LocalDate date, final String name, final Double rewardMoney) {
		Beauty beauty = new Beauty();
		beauty.setCapacity(capacity);
		beauty.setPlace(place);
		beauty.setDate(date);
		beauty.setName(name);
		beauty.setRewardMoney(rewardMoney);

		try {
			this.beautyService.saveBeauty(beauty);
			Assertions.assertThat(beauty.getId()).isNotNull();
		} catch (ReservedDateExeception ex) {
			Logger.getLogger(PetServiceTests.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	@Test
	public void checkFirstPrize() {
		Beauty beauty = this.beautyRepo.findById(3).get();
		Assertions.assertThat(beauty.getFirstClassified()).isEqualTo(beauty.getRewardMoney() * 0.5);

	}

	@Test
	public void checkSecondPrize() {
		Beauty beauty = this.beautyRepo.findById(3).get();
		Assertions.assertThat(beauty.getSecondClassified()).isEqualTo(beauty.getRewardMoney() * 0.35);
	}

	@Test
	public void checkThirdPrize() {
		Beauty beauty = this.beautyRepo.findById(3).get();
		Assertions.assertThat(beauty.getThirdClassified()).isEqualTo(beauty.getRewardMoney() * 0.15);
	}

}