package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Hability;
import org.springframework.samples.petclinic.service.HabilityService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.BDDMockito.given;

import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.mockito.Mockito.verify;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasProperty;


@WebMvcTest(value = HabilityController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class HabilityControllerTests {

	@MockBean
	private UserService userService;

	@MockBean
	private HabilityService habilityService;

	@Autowired
	private MockMvc mockMvc;

	private Hability hability;

	private static final int TEST_HABILITY_ID = 1;

	@BeforeEach
	void setup() {

		this.hability = new Hability();
		hability.setId(TEST_HABILITY_ID);
		hability.setName("Testing");
		hability.setCapacity(10);
		hability.setCircuit("Circuit");
		hability.setDate(LocalDate.of(2020, 12, 1));
		hability.setRewardMoney(1000.00);
		hability.setStatus("DRAFT");
		given(this.habilityService.findHabilityById(TEST_HABILITY_ID)).willReturn(hability);
	}

	@WithMockUser(value = "admin")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/hability/new")).andExpect(status().isOk()).andExpect(model().attributeExists("hability"))
				.andExpect(view().name("tournaments/createOrUpdateHabilityForm"));
	}

	@WithMockUser(value = "admin")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/hability/new").param("name", "Testing").param("capacity", "10")
				.with(csrf())
				.param("circuit", "Circuit")
				.param("date", "2020/12/01")
				.param("rewardMoney", "1000.00")
				.param("status", "DRAFT"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/tournaments"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/hability/new").param("name", "DP")
				.with(csrf())
				.param("circuit", "Circuit")
				.param("date", "2020/12/01")
				.param("rewardMoney", "1000.00")
				.param("status", "DRAFT"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("hability"))
			.andExpect(model().attributeHasFieldErrors("hability", "name"))
			.andExpect(model().attributeHasFieldErrors("hability", "capacity"))
			.andExpect(view().name("tournaments/createOrUpdateHabilityForm"));
	}

	@WithMockUser(value = "admin")
	@Test
	void testInitUpdateHabilityForm() throws Exception {
		mockMvc.perform(get("/tournaments/hability/{habilityId}/edit", TEST_HABILITY_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("hability"))
				.andExpect(model().attribute("hability", hasProperty("name", is("Testing"))))
				.andExpect(model().attribute("hability", hasProperty("capacity", is(10))))
				.andExpect(model().attribute("hability", hasProperty("circuit", is("Circuit"))))
				.andExpect(model().attribute("hability", hasProperty("date", is(LocalDate.of(2020, 12, 1)))))
				.andExpect(model().attribute("hability", hasProperty("rewardMoney", is(1000.00))))
				.andExpect(model().attribute("hability", hasProperty("status", is("DRAFT"))))

				.andExpect(view().name("tournaments/createOrUpdateHabilityForm"));

		verify(habilityService).findHabilityById(TEST_HABILITY_ID);
	}

    @WithMockUser(value = "admin")
	@Test
	void testProcessUpdateHabilityFormSuccess() throws Exception {
		mockMvc.perform(post("/tournaments/hability/{habilityId}/edit", TEST_HABILITY_ID)
							.with(csrf())
							.param("name", "Testing")
							.param("capacity", "10")
							.param("circuit", "Circuit 2")
							.param("date", "2020/12/01")
							.param("rewardMoney", "1000.00")
							.param("status", "DRAFT"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/tournaments"));
	}
    
    @WithMockUser(value = "admin")
   	@Test
   	void testProcessUpdateHabilityFormHasErrors() throws Exception {
   		mockMvc.perform(post("/tournaments/hability/{habilityId}/edit", TEST_HABILITY_ID)
   							.with(csrf())
   							.param("name", "Testing")
   							.param("capacity", "-10")
   							.param("circuit", "Circuit 2")
   							.param("date", "2020/12/01")
   							.param("rewardMoney", "1000.00")
   							.param("status", ""))
   					.andExpect(status().isOk())
 					.andExpect(model().attributeHasErrors("hability"))
   					.andExpect(model().attributeHasFieldErrors("hability", "capacity"))
   					.andExpect(model().attributeHasFieldErrors("hability", "status"))
   					.andExpect(view().name("tournaments/createOrUpdateHabilityForm"));
    }

}
