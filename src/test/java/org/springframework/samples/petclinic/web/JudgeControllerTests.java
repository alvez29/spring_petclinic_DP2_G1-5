package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.service.JudgeService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=OwnerController.class,
	excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
	excludeAutoConfiguration= SecurityConfiguration.class)
public class JudgeControllerTests {

	private static final int TEST_JUDGE_ID = 1;
	
	@Autowired
	private JudgeController judgeController;
	
	@MockBean
	private JudgeService judgeService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Judge judge; 
	
	@BeforeEach
	void setUp() {
		judge = new Judge();
		judge.setId(1);
		judge.setFirstName("Michael");
		judge.setLastName("Scott");
		judge.setCity("Pennsylvania");
		judge.setContact("871729182");
		given(this.judgeService.findJudgeById(TEST_JUDGE_ID)).willReturn(judge);
	}
	
	
}
