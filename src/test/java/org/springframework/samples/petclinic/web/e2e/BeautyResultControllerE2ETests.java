package org.springframework.samples.petclinic.web.e2e;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.web.BeautyResultControllerTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
  webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
public class BeautyResultControllerE2ETests {

	@Autowired
	private MockMvc mockMvc;
	
	
	@WithMockUser(value = "admin")
	@Test
	void beautyResultList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/tournaments/beauty/{tournamentId}/result",3))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("beautyResults"))
		.andExpect(MockMvcResultMatchers.view().name("tournaments/beautyResultList"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testInitCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/tournament/beauty/{tournamentId}/pet/{petId}/add_result", 3, 2))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("resultScore"))
		.andExpect(MockMvcResultMatchers.view().name("/tournaments/createOrUpdateBeautyResultForm"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testInitCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/tournament/beauty/{tournamentId}/pet/{petId}/add_result", 3, 1))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
		.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("resultScore"))
		.andExpect(MockMvcResultMatchers.view().name("redirect:/oups"));
	}
	

	@WithMockUser(value = "admin")
	@Test
	void testProcessCreateFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/tournament/beauty/{tournamentId}/pet/{petId}/add_result", 3, 2)
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("haircut", "10")
				.param("haircutdif", "10")
				.param("technique", "10")
				.param("posture", "10"))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
		.andExpect(MockMvcResultMatchers.view().name("redirect:/tournaments/3"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testProcessCreateFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/tournament/beauty/{tournamentId}/pet/{petId}/add_result", 3, 2)
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("haircut", "110")
				.param("haircutdif", "10")
				.param("technique", "10")
				.param("posture", "10"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("resultScore","haircut"))
		.andExpect(MockMvcResultMatchers.view().name("/tournaments/createOrUpdateBeautyResultForm"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testDelete() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/tournaments/beauty/{tournamentId}/result/{beautyResultId}/delete", 3, 3))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
		.andExpect(MockMvcResultMatchers.view().name("redirect:/tournaments/beauty/{tournamentId}/result"));
	}
	
}
