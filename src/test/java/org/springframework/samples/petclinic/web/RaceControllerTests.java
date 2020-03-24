package org.springframework.samples.petclinic.web;


import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Race;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.RaceService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@WebMvcTest(value = RaceController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class RaceControllerTests {

	@MockBean
	private UserService userService;
	
	@MockBean
	private RaceService raceService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Race race;
	
	private static final int TEST_RACE_ID = 1;
	
	@BeforeEach
	void setup() {
		this.race = new Race();
		race.setId(TEST_RACE_ID);
		race.setName("Testing");
		race.setCapacity(10);
		race.setCanodrome("Canodrome");
		race.setDate(LocalDate.of(2020, 12, 1));
		race.setRewardMoney(1000.00);
		race.setStatus("DRAFT");
		given(this.raceService.findRaceById(TEST_RACE_ID)).willReturn(race);
	}
	
	@WithMockUser(value="admin")
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
				.param("date", "2020/12/01")
				.param("rewardMoney", "1000.00")
				.param("status","DRAFT"))
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
					.andExpect(model().attribute("race", hasProperty("name", is("Testing"))))
					.andExpect(model().attribute("race", hasProperty("canodrome", is("Canodrome"))))
					.andExpect(model().attribute("race", hasProperty("date", is(LocalDate.of(2020, 12, 1)))))
					.andExpect(model().attribute("race", hasProperty("rewardMoney", is(1000.))))
					.andExpect(model().attribute("race", hasProperty("status", is("DRAFT"))))
					.andExpect(view().name("tournaments/createOrUpdateRaceForm"));

			verify(raceService).findRaceById(TEST_RACE_ID);
		}
	 
	 
	 @WithMockUser(value = "admin")
		@Test
		void testProcessUpdateRaceFormSuccess() throws Exception {
			mockMvc.perform(post("/tournaments/race/{raceId}/edit", TEST_RACE_ID)
								.with(csrf())
								.param("name", "Testing Changed")
								.param("canodrome", "Canodrome testing")
								.param("capacity","10")
								.param("date", "2020/12/01")
								.param("rewardMoney", "1000.00")
								.param("status", "DRAFT"))
					.andExpect(status().is3xxRedirection())
					.andExpect(view().name("redirect:/tournaments"));
		}
	 
	 @WithMockUser(value = "admin")
		@Test
		void testProcessUpdateRaceFormHasErrors() throws Exception {
			mockMvc.perform(post("/tournaments/race/{raceId}/edit", TEST_RACE_ID)
								.with(csrf())
								.param("name", "Testing")
								.param("canodrome", "Canodrome")
								.param("capacity", "-10")
								.param("date","2020/12/06")
								.param("status","")
								.param("rewardMoney", "1000.00"))
					.andExpect(status().isOk())
					.andExpect(model().attributeHasErrors("race"))
					.andExpect(model().attributeHasFieldErrors("race", "capacity"))
					.andExpect(model().attributeHasFieldErrors("race", "status"))
					.andExpect(view().name("tournaments/createOrUpdateRaceForm"));
		}

	
	
	

}
