
package org.springframework.samples.petclinic.web.e2e;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
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
public class HabilityResultControllerE2ETests {

	@Autowired
	private MockMvc				mockMvc;

	private static final int	TEST_HABILITY1_ID		= 4;

	private static final int	TEST_HABILITY2_ID		= 5;

	private static final int	TEST_PET1_ID			= 1;

	private static final int	TEST_PET2_ID			= 2;

	private static final int	TEST_HABILITY_RESULT_ID	= 6;


	@WithMockUser(value = "admin")
	@Test
	void habilityResultList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/tournaments/hability/{habilityId}/result", HabilityResultControllerE2ETests.TEST_HABILITY1_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("results")).andExpect(MockMvcResultMatchers.view().name("tournaments/habilityResultList"));
	}

	@WithMockUser(value = "admin")
	@Test
	void testInitCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/tournament/hability/{habilityId}/pet/{petId}/add_result", HabilityResultControllerE2ETests.TEST_HABILITY2_ID, HabilityResultControllerE2ETests.TEST_PET2_ID))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("resultTime")).andExpect(MockMvcResultMatchers.view().name("tournaments/createOrUpdateHabilityResultForm"));
	}

	@WithMockUser(value = "admin")
	@Test
	void testInitCreationFormHasErrors() throws Exception {
		//Obligamos a que salte un error intentando introducir el resultado de un perro que no esta inscrito.
		this.mockMvc.perform(MockMvcRequestBuilders.get("/tournament/hability/{habilityId}/pet/{petId}/add_result", HabilityResultControllerE2ETests.TEST_HABILITY2_ID, HabilityResultControllerE2ETests.TEST_PET1_ID))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("resultTime")).andExpect(MockMvcResultMatchers.view().name("redirect:/oups"));
	}

	@WithMockUser(value = "admin")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/tournament/hability/{tournamentId}/pet/{petId}/add_result", HabilityResultControllerE2ETests.TEST_HABILITY2_ID, HabilityResultControllerE2ETests.TEST_PET2_ID)
			.with(SecurityMockMvcRequestPostProcessors.csrf()).param("time", "40.057")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/tournaments/5"));
	}

	@WithMockUser(value = "admin")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/tournament/hability/{tournamentId}/pet/{petId}/add_result", HabilityResultControllerE2ETests.TEST_HABILITY2_ID, HabilityResultControllerE2ETests.TEST_PET2_ID)
			.with(SecurityMockMvcRequestPostProcessors.csrf()).param("time", "-40.057")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("tournaments/createOrUpdateHabilityResultForm"));
	}

	@WithMockUser(value = "admin")
	@Test
	void testDelete() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/tournaments/hability/{tournamentId}/result/{resultId}/delete", HabilityResultControllerE2ETests.TEST_HABILITY1_ID, HabilityResultControllerE2ETests.TEST_HABILITY_RESULT_ID))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/tournaments/hability/4/result"));
	}

}
