package org.springframework.samples.petclinic.web.e2e;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
//@TestPropertySource( locations = "classpath:application-mysql.properties")
public class JudgeControllerE2ETest {
	
	@Autowired
	private MockMvc mockMvc;
	
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
