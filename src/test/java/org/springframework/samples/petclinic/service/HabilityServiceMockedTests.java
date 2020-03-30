package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Hability;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.model.Sponsor;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.springdatajpa.HabilityRepository;
import org.springframework.samples.petclinic.service.exceptions.JudgeNotFoundException;
import org.springframework.samples.petclinic.service.exceptions.ReservedDateExeception;
import org.springframework.samples.petclinic.service.exceptions.SponsorAmountException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class HabilityServiceMockedTests {
	
	@Mock
	private HabilityRepository habilityRepository;
	
	@Mock
	private PetRepository petRepository;

	protected HabilityService habilityService;
	
	private Hability hability;
	
	private Sponsor sponsor;
	
	private Judge judge;
	
	private List<Sponsor> sponsors;
	
	private List<Judge> judges;
		
	@BeforeEach
	void setUp() {
		this.hability = new Hability();
		this.hability.setId(1);
		this.hability.setCapacity(8000);
		this.hability.setCircuit("Circuit Test");
		this.hability.setDate(LocalDate.of(2040, 6, 8));
		this.hability.setName("Hability ConTEST");
		this.hability.setRewardMoney(1000.00);
		this.hability.setStatus("DRAFT");
		
		this.sponsor = new Sponsor();
		this.sponsor.setName("Sponsor Test");
		this.sponsor.setMoney(10000.00);
		this.sponsors = new ArrayList<Sponsor>();
		this.sponsors.add(sponsor);
		this.hability.setSponsors(this.sponsors);
		
		this.judge = new Judge();
		this.judge.setFirstName("Pepe");
		this.judge.setLastName("Gotera");
		this.judge.setContact("666777888");
		this.judge.setCity("Sevilla");
		this.judges = new ArrayList<Judge>();
		this.judges.add(judge);
		this.hability.setJudges(judges);
		
		habilityService = new HabilityService(habilityRepository, petRepository);
	}
	
	@Test
	void shouldGetSponsorAmount() {
		Sponsor sampleSponsor1 = new Sponsor();
		sampleSponsor1.setMoney(4500.00);
		sampleSponsor1.setName("Sample 1");
		
		Sponsor sampleSponsor2 = new Sponsor();
		sampleSponsor2.setMoney(2000.00);
		sampleSponsor2.setName("Sample 2");
		
		Double sum = sampleSponsor1.getMoney() + sampleSponsor2.getMoney();
		
		Collection<Sponsor> sampleSponsors = new ArrayList<Sponsor>();
		sampleSponsors.add(sampleSponsor1);
		sampleSponsors.add(sampleSponsor2);
		
		//No importa la id del parametro porque estamos mockeando el repositorio
		when(habilityRepository.getSponsors(1)).thenReturn(sampleSponsors);
				
		Double sumTest = this.habilityService.getSponsorAmount(1);
		
		assertThat(sumTest).isEqualTo(sum);
	}
	
	@Test
	void shouldNotSaveHabilityDate() {
		String date = "2040-06-08";
		List<String> dates = new ArrayList<String>();
		dates.add(date);
		
		when(habilityRepository.getFutureDates()).thenReturn(dates);
		
		try {
			habilityService.saveHability(this.hability);
		} catch (ReservedDateExeception ex) {
			assertThat(ex.getClass()).isEqualTo(ReservedDateExeception.class);
		}
	}
	
	@ParameterizedTest
	@CsvSource({"0.00","6999.99"})
	void editHabilityContestSponsorException(Double money) throws ReservedDateExeception, SponsorAmountException, JudgeNotFoundException{
		Sponsor sponsorSample = new Sponsor();
		sponsorSample.setMoney(money);
		sponsorSample.setName("Sample");
		sponsorSample.setUrl("www.sample.com");
		ArrayList<Sponsor> sponsorList = new ArrayList<Sponsor>();
		sponsorList.add(sponsorSample);
		this.hability.setSponsors(sponsorList);
		this.hability.setStatus("PENDING");
		
		when(habilityRepository.getSponsors(1)).thenReturn(sponsorList);
		when(habilityRepository.getJudgesById(1)).thenReturn(this.judges);

		try {
			this.habilityService.editHability(hability);
		} catch (SponsorAmountException ex) {
			assertThat(ex.getClass()).isEqualTo(SponsorAmountException.class);
		}
	}
	
	@Test
	void editHabilityContestDateException() throws ReservedDateExeception, SponsorAmountException, JudgeNotFoundException{
		List<String> dates = new ArrayList<String>();
		dates.add("2040-06-09");
		dates.add("2040-06-08");
		this.hability.setStatus("PENDING");
		when(habilityRepository.getFutureDates()).thenReturn(dates);
		when(habilityRepository.getDateByHanilityId(1)).thenReturn(LocalDate.of(2040, 6, 8));
		when(habilityRepository.getSponsors(1)).thenReturn(this.sponsors);
		when(habilityRepository.getJudgesById(1)).thenReturn(this.judges);
		
		this.hability.setDate(LocalDate.of(2040, 6, 9));
		
		try {
			this.habilityService.editHability(hability);
		} catch (ReservedDateExeception ex) {
			assertThat(ex.getClass()).isEqualTo(ReservedDateExeception.class);
		}
	}
	
	@Test
	void editHabilityNoJudgesException() throws ReservedDateExeception, SponsorAmountException, JudgeNotFoundException{
		List<Judge> emptyJudges =  new ArrayList<Judge>();
		this.hability.setJudges(judges);
		this.hability.setStatus("PENDING");

		when(habilityRepository.getSponsors(1)).thenReturn(this.sponsors);
		when(habilityRepository.getJudgesById(1)).thenReturn(emptyJudges);
		try {
			this.habilityService.editHability(hability);
		} catch (JudgeNotFoundException ex) {
			assertThat(ex.getClass()).isEqualTo(JudgeNotFoundException.class);
		}
	}
}
