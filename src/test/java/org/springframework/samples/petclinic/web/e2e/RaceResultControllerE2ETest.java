package org.springframework.samples.petclinic.web.e2e;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
/*
 * @TestPropertySource( locations = "classpath:application-mysql.properties")
 */
public class RaceResultControllerE2ETest {
	

	@Autowired
	private MockMvc mockMvc;
	
	private static final int TEST_RACE1_ID = 1;
	
	private static final int TEST_RACE2_ID = 2;
	
	private static final int TEST_PET1_ID = 1;

	private static final int TEST_PET2_ID = 2;
	
	private static final int TEST_RACE_RESULT_ID = 1;


	@WithMockUser(value = "admin")
	@Test
	void raceResultList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/tournaments/race/{raceId}/result", TEST_RACE1_ID))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("results"))
		.andExpect(MockMvcResultMatchers.view().name("tournaments/raceResultList"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testInitCreationFormSuccess() throws Exception {
		mockMvc.perform(get("/tournament/race/{raceId}/pet/{petId}/add_result", TEST_RACE2_ID, TEST_PET2_ID)).andExpect(status().isOk()).andExpect(model().attributeExists("resultTime"))
				.andExpect(view().name("tournaments/createOrUpdateRaceResultForm"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testInitCreationFormHasErrors() throws Exception {
		//Obliamos a que salte un error intentando introducir el resultado de un perro que no esta inscrito.
		mockMvc.perform(get("/tournament/race/{raceId}/pet/{petId}/add_result", TEST_RACE2_ID, TEST_PET1_ID)).andExpect(status().is3xxRedirection()).andExpect(model().attributeDoesNotExist("resultTime"))
				.andExpect(view().name("redirect:/oups"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/tournament/race/{tournamentId}/pet/{petId}/add_result", TEST_RACE2_ID, TEST_PET2_ID)
				.with(csrf())
				.param("time", "40.057"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/tournaments/2"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/tournament/race/{tournamentId}/pet/{petId}/add_result", TEST_RACE2_ID, TEST_PET2_ID)
				.with(csrf())
				.param("time", "-40.057"))
		.andExpect(status().isOk())
		.andExpect(view().name("tournaments/createOrUpdateRaceResultForm"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testDelete() throws Exception {
		mockMvc.perform(get("/tournaments/race/{tournamentId}/result/{resultId}/delete", TEST_RACE1_ID, TEST_RACE_RESULT_ID))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/tournaments/race/1/result"));
	}

}
