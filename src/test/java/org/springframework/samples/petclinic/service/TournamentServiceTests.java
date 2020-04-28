package org.springframework.samples.petclinic.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class TournamentServiceTests {

	@Autowired
	private TournamentService tournamnetService;
	
	@Test
	public void shouldGetRacePlace() {
		Assertions.assertThat(this.tournamnetService.getSite(1)).isEqualTo("Gran Hipodromo de Andalucia");
	}
	
	@Test
	public void shouldGetBeautyPlace() {
		Assertions.assertThat(this.tournamnetService.getSite(3)).isEqualTo("Pabellon España");
	}
	
	@Test
	public void shouldGetHabilityPlace() {
		Assertions.assertThat(this.tournamnetService.getSite(4)).isEqualTo("WiZink Center");
	}
	
	@Test
	public void checkPetsWithResults() {
		Integer[] expectedResult = {null, null, 1, 1, 1, null, null, null, null, 1, null, 1};
		Assertions.assertThat(this.tournamnetService.petHasResult(1)).isEqualTo(expectedResult);
	}
}
