
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import org.springframework.samples.petclinic.model.Sponsor;
import org.springframework.samples.petclinic.repository.springdatajpa.BeautyRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedSponsorNameException;
import org.springframework.samples.petclinic.service.exceptions.JudgeNotFoundException;
import org.springframework.samples.petclinic.service.exceptions.ReservedDateExeception;
import org.springframework.samples.petclinic.service.exceptions.SponsorAmountException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BeautyServiceTests {

	@Autowired
	protected BeautyService		beautyService;

	@Autowired
	protected BeautyRepository	beautyRepo;

	@Autowired
	protected SponsorService	sponsorService;


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
	public void editBeautySuccess() throws ReservedDateExeception, SponsorAmountException, JudgeNotFoundException {

		Beauty beauty = this.beautyService.findBeautyById(3);

		beauty.setCapacity(8001);

		this.beautyService.editBeauty(beauty);
		Assertions.assertThat(beauty.getId()).isNotNull();
		Assertions.assertThat(this.beautyService.findBeautyById(3).getCapacity()).isEqualTo(8001);

	}

	@Test
	public void editBeautyDateException() throws SponsorAmountException, JudgeNotFoundException {
		String ex = "";

		Beauty beauty = this.beautyService.findBeautyById(3);
		beauty.setDate(LocalDate.of(2020, 7, 12));
		beauty.setCapacity(1);

		try {
			this.beautyService.editBeauty(beauty);
		} catch (ReservedDateExeception e) {
			Logger.getLogger(BeautyServiceTests.class.getName()).log(Level.SEVERE, null, e);
			ex = e.getClass().getName();
		}

		Assertions.assertThat(ex).isEqualTo(ReservedDateExeception.class.getName());

	}

	@ParameterizedTest
	@CsvSource({
		"0.", "6999.99"
	})
	public void editBeautySponsorException(final Double money) throws ReservedDateExeception, JudgeNotFoundException {
		String ex = "";

		Beauty beauty = this.beautyService.findBeautyById(6);

		Sponsor sponsor = new Sponsor();
		sponsor.setMoney(money);
		sponsor.setName("Testing");
		sponsor.setUrl("http://www.google.com");
		sponsor.setTournament(beauty);

		beauty.setStatus("PENDING");

		try {
			this.beautyService.editBeauty(beauty);
		} catch (SponsorAmountException e) {
			Logger.getLogger(BeautyServiceTests.class.getName()).log(Level.SEVERE, null, e);
			ex = e.getClass().getName();
		}

		Assertions.assertThat(ex).isEqualTo(SponsorAmountException.class.getName());

	}

	@ParameterizedTest
	@CsvSource({
		"PENDING", "FINISHED"
	})
	public void editBeautyWithSponsors(final String status) throws DataAccessException, DuplicatedSponsorNameException, ReservedDateExeception, JudgeNotFoundException {
		Beauty beauty = this.beautyService.findBeautyById(3);
		beauty.setStatus(status);

		Sponsor sponsor = new Sponsor();
		sponsor.setMoney(7000.);
		sponsor.setName("Testing");
		sponsor.setUrl("http://www.google.com");
		sponsor.setTournament(beauty);

		this.sponsorService.saveSponsor(sponsor);

		List<Sponsor> sponsorList = new ArrayList<Sponsor>();
		sponsorList.add(sponsor);

		beauty.setSponsors(sponsorList);

		try {
			this.beautyService.editBeauty(beauty);
		} catch (SponsorAmountException e) {
			Logger.getLogger(BeautyServiceTests.class.getName()).log(Level.SEVERE, null, e);
		}

		Beauty newBeauty = this.beautyService.findBeautyById(3);

		Assertions.assertThat(newBeauty.getSponsors().stream().mapToDouble(x -> x.getMoney()).sum()).isGreaterThanOrEqualTo(7000.);
		Assertions.assertThat(newBeauty.getJudges()).isNotEmpty();
		Assertions.assertThat(newBeauty.getStatus()).isEqualTo(status);
	}

}
