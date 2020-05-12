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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class SponsorControllerE2ETests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@WithMockUser(value = "admin")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(get("/tournaments/{tournamentId}/sponsors/add", 3))
		.andExpect(status().isOk())
		.andExpect(view().name("sponsors/createOrUpdateSponsorForm"));
	}
	
	@WithMockUser(value = "admin")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(post("/tournaments/{tournamentId}/sponsors/add", 3)
						.with(csrf())
						.param("name", "Sponsor de Prueba")
						.param("money", "7000.00")
						.param("url", "https://www.google.es"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/tournaments/{tournamentId}"));
	}
}
