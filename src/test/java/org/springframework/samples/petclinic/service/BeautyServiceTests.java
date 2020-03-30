
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Beauty;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.model.Sponsor;
import org.springframework.samples.petclinic.repository.springdatajpa.BeautyRepository;
import org.springframework.samples.petclinic.service.exceptions.JudgesNotFoundException;
import org.springframework.samples.petclinic.service.exceptions.ReservedDateExeception;
import org.springframework.samples.petclinic.service.exceptions.SponsorAmountException;
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
	public void addNewBeauty(final Integer capacity, final String place, final LocalDate date, final String name, final Double rewardMoney) throws ReservedDateExeception {
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
			Logger.getLogger(BeautyServiceTests.class.getName()).log(Level.SEVERE, null, ex);
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

	@Test
	public void editBeautySuccess() throws ReservedDateExeception, SponsorAmountException, JudgesNotFoundException {
		Beauty beauty = new Beauty();

		beauty.setId(1);
		beauty.setCapacity(8000);
		beauty.setPlace("placeTest");
		beauty.setDate(LocalDate.of(2040, 04, 26));
		beauty.setName("BeautyTest");
		beauty.setRewardMoney(800.00);
		beauty.setStatus("DRAFT");

		this.beautyService.editBeauty(beauty);
		Assertions.assertThat(beauty.getId()).isNotNull();

	}

	@Test
	@Disabled
	public void editBeautyDateException() throws ReservedDateExeception, SponsorAmountException, JudgesNotFoundException {
		Beauty beauty = new Beauty();

		beauty.setId(1);
		beauty.setCapacity(8000);
		beauty.setPlace("placeTest");
		beauty.setDate(LocalDate.of(2020, 8, 06));
		beauty.setName("BeautyTest");
		beauty.setRewardMoney(800.00);
		beauty.setStatus("DRAFT");

		try {
			this.beautyService.editBeauty(beauty);

		} catch (ReservedDateExeception ex) {

			Logger.getLogger(BeautyServiceTests.class.getName()).log(Level.SEVERE, null, ex);
			Assertions.assertThat(beauty.getId()).isNull();

		}

	}

	@ParameterizedTest
	@CsvSource({
		"0.00", "6999.99"
	})
	@Disabled
	public void editBeautySponsorException(final Double money) throws ReservedDateExeception, SponsorAmountException, JudgesNotFoundException {
		Beauty beauty = new Beauty();

		beauty.setId(1);
		beauty.setCapacity(8000);
		beauty.setPlace("placeTest");
		beauty.setDate(LocalDate.of(2020, 8, 06));
		beauty.setName("BeautyTest");
		beauty.setRewardMoney(7000.00);
		beauty.setStatus("PENDING");
		//		Judge judge = new Judge();
		//		judge.setFirstName("Pepe Manuel");
		//		judge.setContact("123456789");
		//		judge.setCity("Madrid");
		//		judge.setLastName("López");
		//		List<Judge> judges = new ArrayList<>();
		//		judges.add(judge);
		//		beauty.setJudges(judges);
		List<Sponsor> sponsors = new ArrayList<>();
		Sponsor sponsor = new Sponsor();
		sponsor.setName("Sponsor1");
		sponsor.setMoney(money);
		sponsors.add(sponsor);
		beauty.setSponsors(sponsors);

		try {
			this.beautyService.editBeauty(beauty);

		} catch (SponsorAmountException ex) {

			Logger.getLogger(BeautyServiceTests.class.getName()).log(Level.SEVERE, null, ex);
			Assertions.assertThat(beauty.getId()).isNull();

		}

	}

	@ParameterizedTest
	@CsvSource({
		"PENDING", "FINISHED"
	})
	@Disabled
	public void editBeautyWithSponsors(final String status) {
		Beauty beauty = new Beauty();

		beauty.setId(1);
		beauty.setCapacity(8000);
		beauty.setPlace("Place Test");
		beauty.setDate(LocalDate.now().plusMonths(1));
		beauty.setName("Beauty Test");
		beauty.setRewardMoney(1000.00);
		beauty.setStatus(status);
		List<Sponsor> sponsors = new ArrayList<Sponsor>();
		Sponsor sponsor = new Sponsor();
		sponsor.setName("Sponsor1");
		sponsor.setMoney(7000.0);
		sponsors.add(sponsor);
		beauty.setSponsors(sponsors);
		Judge judge = new Judge();
		judge.setFirstName("Pepe Manuel");
		judge.setContact("123456789");
		judge.setCity("Madrid");
		judge.setLastName("López");
		List<Judge> judges = new ArrayList<>();
		judges.add(judge);
		beauty.setJudges(judges);
		Assertions.assertThat(beauty.getId()).isNotNull();
		Assertions.assertThat(beauty.getSponsors().stream().mapToDouble(x -> x.getMoney()).sum()).isGreaterThanOrEqualTo(7000.00);

	}

}
