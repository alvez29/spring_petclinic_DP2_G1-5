package org.springframework.samples.petclinic.web.e2e;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;

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
@TestPropertySource( locations = "classpath:application-mysql.properties")
public class TournamentControllerE2ETests {

	@Autowired
	private MockMvc mockMvc;
	
	@WithMockUser(value = "admin")
	@Test
	void testShowTournament() throws Exception {
		mockMvc.perform(get("/tournaments/{tournamentId}", 1)).andExpect(status().isOk())
				.andExpect(model().attribute("tournament", hasProperty("capacity", is(800))))
				.andExpect(model().attribute("tournament", hasProperty("date", is(LocalDate.of(2020, 04, 05)))))
				.andExpect(model().attribute("tournament", hasProperty("name", is("First Race Test"))))
				.andExpect(model().attribute("tournament", hasProperty("rewardMoney", is(7500.00))))
				.andExpect(model().attribute("tournament", hasProperty("status", is("FINISHED"))))
				.andExpect(view().name("tournaments/tournamentDetails"));
	}
	
		
	@WithMockUser(value = "admin")
	@Test
	void testTournamnetsList() throws Exception {
		mockMvc.perform(get("/tournaments")).andExpect(status().isOk()).andExpect(model().attributeExists("tournaments"))
				.andExpect(view().name("tournaments/tournamentList"));
	}
		
	@WithMockUser(value = "admin")
	@Test
	void shouldNotAddPet() throws Exception{
		mockMvc.perform(get("/tournaments/{tournamentId}/addpet/{petId}", 1, 1))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/oups"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void shouldAddJudge() throws Exception {
		mockMvc.perform(get("/tournaments/{tournamentId}/addjudge/{judgeId}", 1, 1))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/tournaments/1"));
	}

	
}