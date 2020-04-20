package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Beauty;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.ResultScore;
import org.springframework.samples.petclinic.model.Sponsor;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.repository.springdatajpa.ResultScoreRepository;
import org.springframework.samples.petclinic.repository.springdatajpa.TournamentRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedResultForPetInTournament;

@ExtendWith(MockitoExtension.class)
public class BeautyResultServiceMockedTest {

	@Mock
	private ResultScoreRepository beautyResultRepo;
	
	@Mock
	private TournamentRepository tournamentRepo;
	
	protected BeautyResultService beautyResultService; 
	
	private Beauty beauty = new Beauty();
	
	private Judge judge = new Judge();
	
	private Sponsor sponsor = new Sponsor();
	
	private Pet dog1 = new Pet();
	
	private Pet dog2 = new Pet();
	
	
	@BeforeEach
	void setUp() {
		beauty.setCapacity(30);
		beauty.setDate(LocalDate.now().plusYears(2));
		beauty.setId(1);
		beauty.setName("Test Tournament");
		beauty.setRewardMoney(1000.);
		beauty.setPlace("Place test");
		beauty.setStatus("FINISHED");
		beauty.setPets(new ArrayList<Pet>());
		
		judge.setCity("City");
		judge.setContact("Contact.com");
		judge.setFirstName("Anne");
		judge.setId(1);
		judge.setLastName("Normal");
		judge.setTournaments(Arrays.asList(beauty));
		beauty.setJudges(Arrays.asList(judge));
		
		sponsor.setId(1);
		sponsor.setMoney(8000.);
		sponsor.setName("Joe");
		sponsor.setUrl("http://www.google.es");
		sponsor.setTournament(beauty);
		beauty.setSponsors(Arrays.asList(sponsor));
		
		PetType petType = new PetType();
		petType.setId(1);
		petType.setName("Example");
		
		dog1.setId(1);
		dog1.setBirthDate(LocalDate.now().minusYears(2));
		dog1.setName("Bobby");
		dog1.setType(petType);
		beauty.addPet(dog1);
		
		dog2.setId(2);
		dog2.setBirthDate(LocalDate.now().minusYears(2));
		dog2.setName("Toby");
		dog2.setType(petType);
		beauty.addPet(dog2);
		
		Optional<Tournament> optionalB = Optional.of(beauty);
		
		Mockito.when(this.beautyResultRepo.hasResult(1, 1)).thenReturn(0);
		Mockito.when(this.tournamentRepo.findById(1)).thenReturn(optionalB);
		
		
		this.beautyResultService = new BeautyResultService(this.beautyResultRepo, this.tournamentRepo);
	}
	
	
	@Test
	void shouldSaveBeautyResult() throws DuplicatedResultForPetInTournament {
		ResultScore resultScore = new ResultScore();
		
		resultScore.setPet(this.dog1);
		resultScore.setHaircut(2);
		resultScore.setHaircutdif(2);
		resultScore.setPosture(2);
		resultScore.setTechnique(2);
		resultScore.setTournament(beauty);
		
		this.beautyResultService.save(resultScore);
		
		Assertions.assertThat(resultScore.getId()).isEqualTo(1);
		
	}
	
	
	
	
}
