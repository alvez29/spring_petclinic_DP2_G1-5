package org.springframework.samples.petclinic.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;

import org.assertj.core.api.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Beauty;
import org.springframework.samples.petclinic.model.Sponsor;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedSponsorNameException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class SponsorServiceTests {

	@Autowired
	protected SponsorService sponsorService;

	@Test
	void shouldSaveSponsor() throws DataAccessException, DuplicatedSponsorNameException {
		Beauty beauty = new Beauty();
		beauty.setId(5);
		beauty.setName("TestingName");
		beauty.setCapacity(10);
		beauty.setDate(LocalDate.of(2020, 12, 23));
		beauty.setPlace("place");
		beauty.setRewardMoney(800.00);
		beauty.setStatus("DRAFT");
		beauty.setSponsors(new ArrayList<Sponsor>());
		
		Sponsor sponsor = new Sponsor();
		sponsor.setMoney(7000.00);
		sponsor.setName("Test");
		sponsor.setUrl("https://www.google.es");
		
		beauty.addSponsor(sponsor);
		this.sponsorService.saveSponsor(sponsor);
		
		
		Assertions.assertThat(sponsor.getId()).isNotNull();
	}
	
	@Test
	void shouldNotSaveSameNameSponsor() throws DuplicatedSponsorNameException {
		Beauty beauty = new Beauty();
		beauty.setId(5);
		beauty.setName("TestingName");
		beauty.setCapacity(10);
		beauty.setDate(LocalDate.of(2020, 12, 23));
		beauty.setPlace("place");
		beauty.setRewardMoney(800.00);
		beauty.setStatus("DRAFT");
		beauty.setSponsors(new ArrayList<Sponsor>());
		
		Sponsor sponsor = new Sponsor();
		sponsor.setMoney(7000.00);
		sponsor.setName("Test");
		sponsor.setUrl("https://www.google.es");
		
		Sponsor sponsor2 = new Sponsor();
		sponsor2.setMoney(7000.00);
		sponsor2.setName("Test");
		sponsor2.setUrl("https://www.google.es");
		
		beauty.addSponsor(sponsor);
		beauty.addSponsor(sponsor2);
		this.sponsorService.saveSponsor(sponsor);
		
		assertThrows(DuplicatedSponsorNameException.class, () ->{
			this.sponsorService.saveSponsor(sponsor2);
		});		
		
		Assertions.assertThat(sponsor.getId()).isNotNull();
	}
	
}
