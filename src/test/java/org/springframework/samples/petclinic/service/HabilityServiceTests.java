package org.springframework.samples.petclinic.service;

import java.time.LocalDate;

import java.util.logging.Logger;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Hability;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.model.Sponsor;
import org.springframework.samples.petclinic.repository.springdatajpa.HabilityRepository;
import org.springframework.samples.petclinic.service.exceptions.JudgeNotFoundException;
import org.springframework.samples.petclinic.service.exceptions.ReservedDateExeception;
import org.springframework.samples.petclinic.service.exceptions.SponsorAmountException;
import org.springframework.stereotype.Service;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class HabilityServiceTests {
	
	@Autowired
	protected HabilityService habilityService;
	
	@Autowired
	protected HabilityRepository habilityRepo;
	
	protected EntityManager entityM;

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
	public void checkThirdPrize() {
		Hability hability = habilityRepo.findById(4).get();
		assertThat(hability.getThirdClassified()).isEqualTo(hability.getRewardMoney()*0.15);
	}
	
	//Pruebas solitarias
	
	@Test
	public void editHabilityContestSuccess() throws ReservedDateExeception, SponsorAmountException, JudgeNotFoundException{
		Hability hability = new Hability();
		hability.setId(1);
		hability.setCapacity(8000);
		hability.setCircuit("Circuit Test");
		hability.setDate(LocalDate.of(2040, 4, 16));
		hability.setName("Hability ConTEST");
		hability.setRewardMoney(1000.00);
		hability.setStatus("DRAFT");		
		this.habilityService.editHability(hability);
		assertThat(hability.getId()).isNotNull();
	}
	
	
	@ParameterizedTest
	@CsvSource({"PENDING","FINISHED"})
	public void editHabilityWithSponsors(String status) {
		Hability hability = new Hability();
		hability.setId(1);
		hability.setCapacity(8000);
		hability.setCircuit("Circuit Test");
		hability.setDate(LocalDate.now().plusMonths(1));
		hability.setName("Hability ConTEST 4");
		hability.setRewardMoney(1000.0);
		hability.setStatus(status);
		List<Sponsor> sponsors = new ArrayList<Sponsor>();
		Sponsor sponsor = new Sponsor();
		sponsor.setName("Sponsor Test");
		sponsor.setMoney(7000.00);
		sponsors.add(sponsor);
		hability.setSponsors(sponsors);
		assertThat(hability.getId()).isNotNull();
		assertThat(hability.getSponsors().stream().mapToDouble(x->x.getMoney()).sum()).isGreaterThanOrEqualTo(7000.00);
	}
	
	@ParameterizedTest
	@CsvSource({"0.","6999.99"})
	@Disabled
	public void editRaceSponsorAmountException(Double money) throws DataAccessException, SponsorAmountException, ReservedDateExeception, JudgeNotFoundException {
		
		Hability hability = new Hability();
		List<Sponsor> sponsors = new ArrayList<Sponsor>();
		Sponsor sponsor = new Sponsor();
		sponsor.setName("Sponsor test");
		sponsor.setMoney(money);
		sponsors.add(sponsor);
		
		hability.setId(1);
		hability.setCapacity(8000);
		hability.setCircuit("Circuit Test");
		hability.setDate(LocalDate.of(2020,6,8));
		hability.setRewardMoney(1000.);
		hability.setName("Race 3 Test");
		hability.setStatus("PENDING");
		hability.setSponsors(sponsors);
		
		Judge j1 = new Judge();
		j1.setCity("Sevilla");
		j1.setFirstName("Pepe");
		j1.setLastName("Gotera");
		j1.setContact("666666666");
		
		List<Judge> judges = new ArrayList<Judge>();
		
		hability.setJudges(judges);
		
		hability.addJudge(j1);

//		this.entityM.persist(sponsor);
//		this.entityM.persist(judges);
//		this.entityM.persist(race);
//		this.entityM.merge(race);
//		this.entityM.flush();	
		
		try{
			this.habilityService.editHability(hability);
		}catch(SponsorAmountException e){
			Logger.getLogger(PetServiceTests.class.getName()).log(Level.SEVERE, null, e);
	        assertThat(hability.getId()).isNull();
		}

	}
	
	
		
}