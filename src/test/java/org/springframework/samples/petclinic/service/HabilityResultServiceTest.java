
package org.springframework.samples.petclinic.service;

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
import org.springframework.samples.petclinic.model.Hability;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.ResultTime;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedResultForPetInTournament;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class HabilityResultServiceTest {

	@Autowired
	protected HabilityResultService	habilityResultService;

	@Autowired
	protected HabilityService		habilityService;

	@Autowired
	protected PetService			petService;


	@Test
	void shouldSavePetResult() {
		ResultTime resultTime = new ResultTime();
		Pet pet = this.petService.findPetById(12);
		resultTime.setTime(40.);
		resultTime.setLowFails(1);
		resultTime.setMediumFails(1);
		resultTime.setBigFails(1);
		resultTime.setPet(pet);
		Hability hability = this.habilityService.findHabilityById(4);
		resultTime.setTournament(hability);
		try {
			this.habilityResultService.saveResult(resultTime);
		} catch (DuplicatedResultForPetInTournament ex) {
			Logger.getLogger(HabilityResultServiceTest.class.getName()).log(Level.SEVERE, null, ex);

		}
		Assertions.assertThat(resultTime.getId()).isNotNull();
	}

	@Test
	void shouldNotSaveDuplicatedPetResult() throws DuplicatedResultForPetInTournament {
		ResultTime resultTime = new ResultTime();
		Pet pet = this.petService.findPetById(1);
		resultTime.setTime(40.);
		resultTime.setLowFails(1);
		resultTime.setMediumFails(1);
		resultTime.setBigFails(1);
		resultTime.setPet(pet);
		Hability hability = this.habilityService.findHabilityById(4);
		resultTime.setTournament(hability);
		try {
			this.habilityResultService.saveResult(resultTime);
		} catch (DuplicatedResultForPetInTournament ex) {
			Logger.getLogger(HabilityResultServiceTest.class.getName()).log(Level.SEVERE, null, ex);
		}
		Assertions.assertThat(resultTime.getId()).isNull();
	}

	@Test
	void ShouldDeletePetResult() {

		//Comprobamos que el torneo 1 contiene el resultado con ID 1
		List<Integer> resultsId1 = this.habilityResultService.findByTournamentId(4).stream().map(ResultTime::getId).collect(Collectors.toList());
		Assertions.assertThat(resultsId1.contains(6)).isTrue();

		// Eliminamos el resultado de Basil en la carrera con ID 1
		this.habilityResultService.deleteResult(6);

		//Verificamos que ahora no está el resultado 1 en el torneo 1
		List<Integer> resultsId = this.habilityResultService.findByTournamentId(1).stream().map(ResultTime::getId).collect(Collectors.toList());
		Assertions.assertThat(resultsId.contains(6)).isFalse();

	}

	@Test
	void petShouldBeInTournament() {
		Assertions.assertThat(this.habilityResultService.isInTournament(4, 12)).isTrue();
	}

	@Test
	void petShouldNotBeInTournament() {
		Assertions.assertThat(this.habilityResultService.isInTournament(4, 2)).isFalse();
	}

}
