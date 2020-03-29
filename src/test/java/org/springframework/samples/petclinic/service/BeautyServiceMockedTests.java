
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Beauty;
import org.springframework.samples.petclinic.model.Sponsor;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.springdatajpa.BeautyRepository;
import org.springframework.samples.petclinic.service.exceptions.ReservedDateExeception;

@ExtendWith(MockitoExtension.class)
public class BeautyServiceMockedTests {

	@Mock
	private BeautyRepository	beautyRepo;

	@Mock
	private PetRepository		petRepo;

	protected BeautyService		beautyService;


	@BeforeEach
	void setUp() {
		this.beautyService = new BeautyService(this.beautyRepo, this.petRepo);

	}

	@Test
	void shouldGetSponsorAmount() {
		Sponsor sponsorSample = new Sponsor();
		sponsorSample.setMoney(4500.00);
		sponsorSample.setName("Sample 1");
		Sponsor sponsorSample2 = new Sponsor();
		sponsorSample2.setMoney(2000.00);
		sponsorSample2.setName("Sample 2");
		Collection<Sponsor> sampleSponsors = new ArrayList<Sponsor>();
		sampleSponsors.add(sponsorSample);
		sampleSponsors.add(sponsorSample2);

		// No importa la id del parametro porque estamos mockeando el repositorio
		Mockito.when(this.beautyRepo.getSponsors(1)).thenReturn(sampleSponsors);

		Double sum = this.beautyService.getSponsorAmount(1);
		Double sumMoney = sponsorSample.getMoney() + sponsorSample2.getMoney();

		Assertions.assertThat(sum).isEqualTo(sumMoney);

	}

	@Test
	void shoulNotSaveBeautyDate() {
		String date = "2040-06-08";
		List<String> dates = new ArrayList<String>();
		dates.add(date);

		Mockito.when(this.beautyRepo.getFutureDates()).thenReturn(dates);
		Beauty beauty = new Beauty();
		beauty.setCapacity(10);
		beauty.setPlace("placeTest");
		beauty.setName("NameTest");
		beauty.setRewardMoney(800.00);
		beauty.setDate(LocalDate.of(2040, 6, 8));

		try {
			this.beautyService.saveBeauty(beauty);
		} catch (ReservedDateExeception ex) {
			Assertions.assertThat(beauty.getId()).isNull();
		}

	}

}
