package org.springframework.samples.petclinic.service.mysql;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.service.JudgeService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JudgeServiceTests {

	@Autowired
	protected JudgeService judgeService;
	
	@Test
	void shouldFindWithCorrectId() {
		Judge judge = this.judgeService.findJudgeById(1);
		Assertions.assertThat(judge.getFirstName()).isEqualTo("Juez");
		Assertions.assertThat(judge.getCity()).isEqualTo("Sevilla");
	}

	@Test
	void shouldSaveJudge() {
		Judge judge = new Judge();
		judge.setFirstName("Paco");
		judge.setLastName("Perez");
		judge.setCity("Huelva");
		judge.setContact("718731872");
		this.judgeService.saveJudge(judge);
		Assertions.assertThat(judge.getId()).isNotNull();
	}

	
}
