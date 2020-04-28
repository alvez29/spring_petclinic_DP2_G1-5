package org.springframework.samples.petclinic.service.mysql;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Race;
import org.springframework.samples.petclinic.model.ResultTime;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.RaceResultService;
import org.springframework.samples.petclinic.service.RaceService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedResultForPetInTournament;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class RaceResultServiceTests {


	@Autowired
	protected RaceResultService raceResultService;

	@Autowired
	protected RaceService raceService;

	@Autowired
	protected PetService petService;

	@Test
	void shouldSavePetResult() {

		ResultTime resultTime = new ResultTime();
		// Introducimoslos datos de Basil en el torneo
		Pet pet2 = this.petService.findPetById(2);
		resultTime.setTime(40.0);
		resultTime.setPet(pet2);
		Race race2 = this.raceService.findRaceById(2);
		resultTime.setTournament(race2);

		try {
			this.raceResultService.saveResult(resultTime);
		} catch (DuplicatedResultForPetInTournament ex) {
			Logger.getLogger(RaceResultServiceTests.class.getName()).log(Level.SEVERE, null, ex);
		}
		assertThat(resultTime.getId()).isNotNull();
	}

	@Test
	void shouldNotSaveDuplicatedPetResult() {
		// Introducimos los datos de Basil en el torneo 1, donde ya tiene registrado un
		// resultado
		ResultTime resultTime = new ResultTime();
		Pet pet2 = this.petService.findPetById(2);
		resultTime.setPet(pet2);
		resultTime.setTime(40.0);
		Race race1 = this.raceService.findRaceById(1);
		resultTime.setTournament(race1);

		try {
			this.raceResultService.saveResult(resultTime);
		} catch (DuplicatedResultForPetInTournament ex) {
			// Saltará una excepción porque ese perro ya tine resultados asociados
			Logger.getLogger(RaceResultServiceTests.class.getName()).log(Level.SEVERE, null, ex);
		}
		assertThat(resultTime.getId()).isNull();
	}

	@Test
	void petShouldBeInTournament() {
		// comprobamos que Basil, el perro con ID 2 al que le añadimos un resultado,
		// está en el torneo
		assertThat(this.raceResultService.isInTournament(2, 2)).isTrue();

	}

	@Test
	void petShouldNotBeInTournament() {
		// comprobamos que Leo, el perro con ID 1, no está en la lista de participantes
		assertThat(this.raceResultService.isInTournament(2, 1)).isFalse();
	}

	@Test
	void shoulDeletePetResult() {
		//Comprobamos que el torneo 1 contiene el resultado con ID 1
		List<Integer> resultsId1 = this.raceResultService.findByTournamnetId(1).stream()
				.map(ResultTime::getId)
				.collect(Collectors.toList());
		assertThat(resultsId1.contains(1)).isTrue();
		
		// Eliminamos el resultado de Basil en la carrera con ID 1
		this.raceResultService.deleteRaceResult(1);
		
		//Verificamos que ahora no está el resultado 1 en el torneo 1
		List<Integer> resultsId = this.raceResultService.findByTournamnetId(1).stream()
				.map(ResultTime::getId)
				.collect(Collectors.toList());
		assertThat(resultsId.contains(1)).isFalse();
	}

}
