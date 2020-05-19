package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Race;
import org.springframework.samples.petclinic.model.Sponsor;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.springdatajpa.RaceRepository;
import org.springframework.samples.petclinic.service.exceptions.ReservedDateExeception;

@ExtendWith(MockitoExtension.class)
public class RaceServiceMockedTest {

	@Mock
	private RaceRepository raceRepository;

	@Mock
	private PetRepository petRepository;

	protected RaceService raceService;

	@BeforeEach
	void setup() {
		raceService = new RaceService(raceRepository, petRepository);	
	}

	@Test
	void shouldGetSponsorAmount() {
		Sponsor sampleSponsor1 = new Sponsor();
		Sponsor sampleSponsor2 = new Sponsor();

		sampleSponsor1.setName("SponsorTest1");
		sampleSponsor2.setName("SponsorTest2");

		sampleSponsor1.setMoney(4500.);
		sampleSponsor2.setMoney(2000.);

		Collection<Sponsor> sampleSponsors = new ArrayList<Sponsor>();
		sampleSponsors.add(sampleSponsor1);
		sampleSponsors.add(sampleSponsor2);

		// No importa la id del parametro porque estamos mockeando el repositorio
		when(raceRepository.getSponsors(1)).thenReturn(sampleSponsors);

		Double sumToTest = raceService.getSponsorAmount(1);
		Double sum = sampleSponsor1.getMoney() + sampleSponsor2.getMoney();

		assertThat(sumToTest).isEqualTo(sum);

	}

	@Test
	void shouldNotSaveRace() {

		String fecha = "2040-06-08";
		List<String> dates = new ArrayList<String>();
		dates.add(fecha);
		
		when(raceRepository.getFutureDates()).thenReturn(dates);
		
		Race race = new Race();
		race.setName("Testing");
		race.setDate(LocalDate.of(2040, 6, 8));
		race.setCapacity(8000);
		race.setRewardMoney(2000.);
		race.setCanodrome("Testing");
		
		String exceptionClass = "";
		
		try {
			raceService.saveRace(race);
		}catch (ReservedDateExeception e) {
			exceptionClass = e.getClass().getSimpleName();
		}
		
		assertThat(exceptionClass).isEqualTo(ReservedDateExeception.class.getSimpleName());

	}
	
	
}
	
