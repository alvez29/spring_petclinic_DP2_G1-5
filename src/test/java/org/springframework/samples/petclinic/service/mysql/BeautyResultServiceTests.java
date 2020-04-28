package org.springframework.samples.petclinic.service.mysql;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Beauty;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.ResultScore;
import org.springframework.samples.petclinic.service.BeautyResultService;
import org.springframework.samples.petclinic.service.BeautyService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedResultForPetInTournament;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class BeautyResultServiceTests {

	@Autowired
	protected BeautyResultService beautyResultService;
	
	@Autowired
	protected BeautyService beautyService;
	
	@Autowired
	protected PetService petService;
	
	@Test
	void shouldSavePetResult() {
		ResultScore resultScore = new ResultScore();
		Pet petBasil = this.petService.findPetById(2);
		resultScore.setPet(petBasil);
		resultScore.setHaircut(10);
		resultScore.setHaircutdif(10);
		resultScore.setPosture(10);
		resultScore.setTechnique(10);
		Beauty beauty = this.beautyService.findBeautyById(3);
		resultScore.setTournament(beauty);
		
		try {
			this.beautyResultService.save(resultScore);
		}catch (DuplicatedResultForPetInTournament e) {
			Logger.getLogger(BeautyResultServiceTests.class.getName()).log(Level.SEVERE, null, e);
		}
		Assertions.assertThat(resultScore.getId()).isNotNull();
	}
	
	@Test
	void shouldNotSaveDuplicatedPetResult() {
		ResultScore resultScore = new ResultScore();
		Pet petRosy = this.petService.findPetById(3);
		resultScore.setPet(petRosy);
		resultScore.setHaircut(10);
		resultScore.setHaircutdif(10);
		resultScore.setPosture(10);
		resultScore.setTechnique(10);
		Beauty beauty = this.beautyService.findBeautyById(3);
		resultScore.setTournament(beauty);
		
		try {
			this.beautyResultService.save(resultScore);
		}catch (DuplicatedResultForPetInTournament e) {
			Logger.getLogger(BeautyResultServiceTests.class.getName()).log(Level.SEVERE, null, e);
		}
		
		Assertions.assertThat(resultScore.getId()).isNull();
	}
	
	@Test
	void shouldDeletePetResult() {
		

//Comprobamos que el torneo 1 contiene el resultado con ID 1
		List<Integer> resultsId1 = this.beautyResultService.findByTournamentId(3).stream()
				.map(ResultScore::getId)
				.collect(Collectors.toList());
		Assertions.assertThat(resultsId1.contains(2)).isTrue();
		
		// Eliminamos el resultado de Basil en la carrera con ID 1
		this.beautyResultService.delete(2);
		
		//Verificamos que ahora no está el resultado 1 en el torneo 1
		List<Integer> resultsId = this.beautyResultService.findByTournamentId(3).stream()
				.map(ResultScore::getId)
				.collect(Collectors.toList());
		Assertions.assertThat(resultsId.contains(1)).isFalse();
		
	}
	
	
	@Test
	void petShouldBeInTournament() {
		Assertions.assertThat(this.beautyResultService.isInTournament(3, 3)).isTrue();
	}
	
	@Test
	void petShouldNotBeInTournament() {
		Assertions.assertThat(this.beautyResultService.isInTournament(3, 1)).isFalse();
	}
	
	
}
