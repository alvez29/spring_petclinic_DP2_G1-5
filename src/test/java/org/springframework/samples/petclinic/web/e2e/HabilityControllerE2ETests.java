
package org.springframework.samples.petclinic.web.e2e;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional

@TestPropertySource( locations = "classpath:application-mysql.properties")

public class HabilityControllerE2ETests {

	@Autowired
	private MockMvc				mockMvc;

	private static final int	TEST_HABILITY_ID	= 4;


	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/hability/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("hability"))
			.andExpect(MockMvcResultMatchers.view().name("tournaments/createOrUpdateHabilityForm"));
	}

	@WithMockUser(value = "admin")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/hability/new").param("name", "Test").param("capacity", "10").with(SecurityMockMvcRequestPostProcessors.csrf()).param("circuit", "Circuit").param("date", "2020/12/02")
			.param("rewardMoney", "1000.00").param("status", "DRAFT")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/tournaments"));

	}

	@WithMockUser(value = "admin")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/hability/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "xx").param("circuit", "Circuit").param("date", "2020-12-01").param("rewardMoney", "1000.00").param("status", "DRAFT"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("hability")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("hability", "name"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("hability", "capacity")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("hability", "date"))
			.andExpect(MockMvcResultMatchers.view().name("tournaments/createOrUpdateHabilityForm"));

	}

	@WithMockUser(value = "admin")
	@Test
	void testInitUpdateHabilityForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/tournaments/hability/{habilityId}/edit", HabilityControllerE2ETests.TEST_HABILITY_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("hability")).andExpect(MockMvcResultMatchers.model().attribute("hability", Matchers.hasProperty("name", Matchers.is("Hability ConTEST"))))
			.andExpect(MockMvcResultMatchers.model().attribute("hability", Matchers.hasProperty("circuit", Matchers.is("WiZink Center"))))
			.andExpect(MockMvcResultMatchers.model().attribute("hability", Matchers.hasProperty("date", Matchers.is(LocalDate.of(2020, 7, 10)))))
			.andExpect(MockMvcResultMatchers.model().attribute("hability", Matchers.hasProperty("rewardMoney", Matchers.is(8000.)))).andExpect(MockMvcResultMatchers.model().attribute("hability", Matchers.hasProperty("capacity", Matchers.is(800))))
			.andExpect(MockMvcResultMatchers.model().attribute("hability", Matchers.hasProperty("status", Matchers.is("DRAFT")))).andExpect(MockMvcResultMatchers.view().name("tournaments/createOrUpdateHabilityForm"));
	}

	@WithMockUser(value = "admin")
	@Test
	void testProcessUpdateHabilityFormSuccess() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/tournaments/hability/{habilityId}/edit", HabilityControllerE2ETests.TEST_HABILITY_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "Hability ConTEST").param("circuit", "WiZink Center")
				.param("capacity", "800").param("date", "2020/07/10").param("rewardMoney", "7700.").param("status", "DRAFT"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/tournaments"));
	}

	@WithMockUser(value = "admin")
	@Test
	void testProcessUpdateHabilityFormHasErrors() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/tournaments/hability/{habilityId}/edit", HabilityControllerE2ETests.TEST_HABILITY_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "Hability ConTEST").param("circuit", "WiZink Center")
				.param("capacity", "-800").param("date", "2020/07/12").param("rewardMoney", "8000.").param("status", ""))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("hability")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("hability", "capacity"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("hability", "status")).andExpect(MockMvcResultMatchers.view().name("tournaments/createOrUpdateHabilityForm"));
	}

}
