package org.springframework.samples.petclinic.web.e2e;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
/*
 * @TestPropertySource( locations = "classpath:application-mysql.properties")
 */
public class RaceControllerE2ETest {

	@Autowired
	private MockMvc mockMvc;
	
	private static final int TEST_RACE_ID = 2;


	@WithMockUser(username = "admin1", authorities = { "admin" })
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/race/new")).andExpect(status().isOk()).andExpect(model().attributeExists("race"))
				.andExpect(view().name("tournaments/createOrUpdateRaceForm"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/race/new")
				.param("name", "Testing")
				.param("capacity", "10")
				.with(csrf())
				.param("canodrome", "Canodrome")
				.param("date", "2042/12/01")
				.param("rewardMoney", "1000.00"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/tournaments"));

	}
	
	@WithMockUser(value = "admin")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/race/new").with(csrf())
				.param("name", "Er")
				.param("canodrome", "Canodrome")
				.param("date", "2020-12-01")
				.param("rewardMoney", "1000.00")
				.param("status", "DRAFT"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("race"))
				.andExpect(model().attributeHasFieldErrors("race", "name"))
				.andExpect(model().attributeHasFieldErrors("race", "capacity"))
				.andExpect(model().attributeHasFieldErrors("race", "date"))
				.andExpect(view().name("tournaments/createOrUpdateRaceForm"));
		
	}
	
	 @WithMockUser(value = "admin")
		@Test
		void testInitUpdateRaceForm() throws Exception {
			mockMvc.perform(get("/tournaments/race/{raceId}/edit", TEST_RACE_ID))
			.andExpect(status().isOk())
					.andExpect(model().attributeExists("race"))
					.andExpect(model().attribute("race", hasProperty("name", is("Second Race Test"))))
					.andExpect(model().attribute("race", hasProperty("canodrome", is("Lorem Ipsum Patata"))))
					.andExpect(model().attribute("race", hasProperty("date", is(LocalDate.of(2020, 6, 14)))))
					.andExpect(model().attribute("race", hasProperty("rewardMoney", is(7500.))))
					.andExpect(model().attribute("race", hasProperty("capacity", is(800))))					
					.andExpect(model().attribute("race", hasProperty("status", is("DRAFT"))))
					.andExpect(view().name("tournaments/createOrUpdateRaceForm"));
		}
	 
	 
	 @WithMockUser(value = "admin")
		@Test
		void testProcessUpdateRaceFormSuccess() throws Exception {
			mockMvc.perform(post("/tournaments/race/{raceId}/edit", TEST_RACE_ID)
								.with(csrf())
								.param("name", "Second Race Test")
								.param("canodrome", "Lorem Ipsum Patata")
								.param("capacity","800")
								.param("date", "2020/06/14")
								.param("rewardMoney", "7700.")
								.param("status", "DRAFT"))
					.andExpect(status().is3xxRedirection())
					.andExpect(view().name("redirect:/tournaments"));
		}
	 
	 @WithMockUser(value = "admin")
		@Test
		void testProcessUpdateRaceFormHasErrors() throws Exception {
			mockMvc.perform(post("/tournaments/race/{raceId}/edit", TEST_RACE_ID)
								.with(csrf())
								.param("name", "Second Race Test")
								.param("canodrome", "Lorem Ipsum Patata")
								.param("capacity","-800")
								.param("date", "2020/06/14")
								.param("rewardMoney", "7700.")
								.param("status", ""))
					.andExpect(status().isOk())
					.andExpect(model().attributeHasErrors("race"))
					.andExpect(model().attributeHasFieldErrors("race", "capacity"))
					.andExpect(model().attributeHasFieldErrors("race", "status"))
					.andExpect(view().name("tournaments/createOrUpdateRaceForm"));
		}

}