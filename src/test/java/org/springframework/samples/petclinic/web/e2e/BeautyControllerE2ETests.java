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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class BeautyControllerE2ETests {

	@Autowired
	private MockMvc mockMvc;
	
	@WithMockUser(value = "admin")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/beauty/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("beauty"))
			.andExpect(MockMvcResultMatchers.view().name("tournaments/createOrUpdateBeautyForm"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/beauty/new").param("name", "Testing").param("capacity", "10").with(SecurityMockMvcRequestPostProcessors.csrf()).param("date", "2020/12/12").param("place", "placeTest")
			.param("rewardMoney", "800.00").param("status", "DRAFT")).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testInitUpdateBeautyForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/tournaments/beauty/{beautyId}/edit", 3)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("beauty"))
			.andExpect(MockMvcResultMatchers.model().attribute("beauty", Matchers.hasProperty("name", Matchers.is("Beauty ConTEST"))))
			.andExpect(MockMvcResultMatchers.model().attribute("beauty", Matchers.hasProperty("capacity", Matchers.is(800))))
			.andExpect(MockMvcResultMatchers.model().attribute("beauty", Matchers.hasProperty("date", Matchers.is(LocalDate.of(2020, 04, 06)))))
			.andExpect(MockMvcResultMatchers.model().attribute("beauty", Matchers.hasProperty("place", Matchers.is("Pabellon Principe Felipe"))))
			.andExpect(MockMvcResultMatchers.model().attribute("beauty", Matchers.hasProperty("rewardMoney", Matchers.is(7500.00))))
			.andExpect(MockMvcResultMatchers.model().attribute("beauty", Matchers.hasProperty("status", Matchers.is("FINISHED"))))
			.andExpect(MockMvcResultMatchers.view().name("tournaments/createOrUpdateBeautyForm"));

	}
	
	@WithMockUser(value = "admin")
	@Test
	void testProcessUpdateBeautyFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/tournaments/beauty/{beautyId}/edit", 6).with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("name", "Beauty ConTEST2")
				.param("capacity", "800")
				.param("status", "DRAFT")
			.param("place", "Pabellon España")
			.param("date", "2022/06/04")
			.param("rewardMoney", "1000"))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/tournaments"));
	}

	@WithMockUser(value = "admin")
	@Test
	void testProcessUpdateBeautyFormHasErrors() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/tournaments/beauty/{beautyId}/edit", 6).with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "BeautyContest2").param("capacity", "-10").param("place", "place2")
				.param("date", "place").param("rewardMoney", "800.00").param("status", "DRAFT"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("beauty")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("beauty", "capacity"))
			.andExpect(MockMvcResultMatchers.view().name("tournaments/createOrUpdateBeautyForm"));
	}
	
	
}
