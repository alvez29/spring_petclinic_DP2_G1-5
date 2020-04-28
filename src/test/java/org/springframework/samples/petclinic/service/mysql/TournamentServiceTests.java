package org.springframework.samples.petclinic.service.mysql;

import java.nio.charset.StandardCharsets;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class TournamentServiceTests {
	

	@Autowired
	private TournamentService tournamnetService;
	
	@Test
	public void shouldGetRacePlace() {
		Assertions.assertThat(this.tournamnetService.getSite(1)).isEqualTo("Gran Hipodromo de Andalucia");
	}
	
	@Test
	public void shouldGetBeautyPlace() {
		String place = this.tournamnetService.getSite(3);
		byte[] b = place.getBytes();
		String ans = new String(b,StandardCharsets.UTF_8);
		Assertions.assertThat(ans).isEqualTo("Pabellon España");
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
