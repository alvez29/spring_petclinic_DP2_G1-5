
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Beauty;
import org.springframework.samples.petclinic.service.BeautyService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// @Autowired
// private BeautyController beautyController;

@WebMvcTest(controllers = BeautyController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class BeautyControllerTest {

	@MockBean
	private BeautyService		beautyService;

	@Autowired
	private MockMvc				mockMvc;

	private Beauty				beauty;

	private static final int	TEST_BEAUTY_ID	= 1;


	@BeforeEach
	void setUp() {
		this.beauty = new Beauty();
		this.beauty.setId(5);
		this.beauty.setName("TestingName");
		this.beauty.setCapacity(10);
		this.beauty.setDate(LocalDate.of(2020, 12, 23));
		this.beauty.setPlace("place");
		this.beauty.setRewardMoney(800.00);
		this.beauty.setStatus("DRAFT");

		BDDMockito.given(this.beautyService.findBeautyById(BeautyControllerTest.TEST_BEAUTY_ID)).willReturn(this.beauty);
	}

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
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/beauty/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "Te").param("date", "2020/12/12").param("date", "2020/12/12").param("place", "placeTest").param("rewardMoney", "800.00")
				.param("status", "DRAFT"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("beauty")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("beauty", "name"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("beauty", "capacity")).andExpect(MockMvcResultMatchers.view().name("tournaments/createOrUpdateBeautyForm"));
	}

	@WithMockUser(value = "admin")
	@Test
	void testInitUpdateBeautyForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/tournaments/beauty/{beautyId}/edit", BeautyControllerTest.TEST_BEAUTY_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("beauty"))
			.andExpect(MockMvcResultMatchers.model().attribute("beauty", Matchers.hasProperty("name", Matchers.is("TestingName")))).andExpect(MockMvcResultMatchers.model().attribute("beauty", Matchers.hasProperty("capacity", Matchers.is(10))))
			.andExpect(MockMvcResultMatchers.model().attribute("beauty", Matchers.hasProperty("date", Matchers.is(LocalDate.of(2020, 12, 23)))))
			.andExpect(MockMvcResultMatchers.model().attribute("beauty", Matchers.hasProperty("place", Matchers.is("place")))).andExpect(MockMvcResultMatchers.model().attribute("beauty", Matchers.hasProperty("rewardMoney", Matchers.is(800.00))))
			.andExpect(MockMvcResultMatchers.model().attribute("beauty", Matchers.hasProperty("status", Matchers.is("DRAFT")))).andExpect(MockMvcResultMatchers.view().name("tournaments/createOrUpdateBeautyForm"));

		Mockito.verify(this.beautyService).findBeautyById(BeautyControllerTest.TEST_BEAUTY_ID);
	}

	@WithMockUser(value = "admin")
	@Test
	void testProcessUpdateBeautyFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/tournaments/beauty/{beautyId}/edit", BeautyControllerTest.TEST_BEAUTY_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "TestingName").param("capacity", "10")
			.param("place", "place2").param("date", "2020/12/23").param("rewardMoney", "800.00").param("status", "DRAFT")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/tournaments"));
	}

	@WithMockUser(value = "admin")
	@Test
	void testProcessUpdateBeautyFormHasErrors() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/tournaments/beauty/{beautyId}/edit", BeautyControllerTest.TEST_BEAUTY_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "TestingName").param("capacity", "-10").param("place", "place")
				.param("date", "place").param("rewardMoney", "800.00").param("status", "DRAFT"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("beauty")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("beauty", "capacity"))
			.andExpect(MockMvcResultMatchers.view().name("tournaments/createOrUpdateBeautyForm"));
	}

}
