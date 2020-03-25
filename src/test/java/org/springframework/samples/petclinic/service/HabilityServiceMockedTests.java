package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Hability;
import org.springframework.samples.petclinic.model.Sponsor;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.springdatajpa.HabilityRepository;
import org.springframework.samples.petclinic.service.exceptions.ReservedDateExeception;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class HabilityServiceMockedTests {
	
	@Mock
	private HabilityRepository habilityRepository;
	
	@Mock
	private PetRepository petRepository;

	protected HabilityService habilityService;
		
	@BeforeEach
	void setUp() {
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
		
		Hability hability = new Hability();
		hability.setCapacity(1000);
		hability.setCircuit("Circuit");
		hability.setName("Testing Stubs");
		hability.setRewardMoney(200.00);
		hability.setDate(LocalDate.of(2040, 6, 8));
		
		try {
			habilityService.saveHability(hability);
		} catch (ReservedDateExeception ex) {
			assertThat(hability.getId()).isNull();
		}
	}
}
