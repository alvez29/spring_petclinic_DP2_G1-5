package org.springframework.samples.petclinic.web;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.JudgeService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.BDDMockito.given;

import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers=JudgeController.class,
	excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
	excludeAutoConfiguration= SecurityConfiguration.class)
public class JudgeControllerTests {

	private static final int TEST_JUDGE_ID = 1;
	
	@MockBean
	private JudgeService judgeService;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private AuthoritiesService authoritiesService;
	
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
	
	
	@WithMockUser(value = "admin")
		@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/judge/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("judge/createOrUpdateJudgeForm"));
	}
	
	
	@WithMockUser(value = "admin")
		@Test
	void testProcessCreationFormSucess() throws Exception {
		mockMvc.perform(post("/judge/new")
					.with(csrf())
					.param("firstName", "Michael")
					.param("lastName", "Scott")
					.param("contact", "871729182")
					.param("city", "Pennsylavania"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/judge"));
	}

}
	

