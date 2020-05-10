package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Beauty;
import org.springframework.samples.petclinic.model.Sponsor;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.BeautyService;
import org.springframework.samples.petclinic.service.SponsorService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = SponsorController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class SponsorControllerTests {
	
	private static final int TEST_BEAUTY_ID = 3;
	
	@Autowired
	private SponsorController sponsorController;
	
	@MockBean
	private TournamentService tournamentService;
	
	@MockBean
	private SponsorService sponsorService;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private BeautyService beautyService;
	
	@MockBean
	private AuthoritiesService authoritiesService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Sponsor sponsor; 
	
	@BeforeEach
	void setUp() {
		Beauty beauty = new Beauty();
		beauty.setName("TestingName");
		beauty.setCapacity(10);
		beauty.setDate(LocalDate.of(2020, 12, 23));
		beauty.setPlace("place");
		beauty.setRewardMoney(800.00);
		beauty.setStatus("DRAFT");
		beauty.setSponsors(new ArrayList<Sponsor>());
	
		given(this.tournamentService.findTournamentById(TEST_BEAUTY_ID)).willReturn(beauty);
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testInitCreationForm() throws Exception {
	mockMvc.perform(get("/tournaments/{tournamentId}/sponsors/add", TEST_BEAUTY_ID))
		.andExpect(status().isOk())
		.andExpect(view().name("sponsors/createOrUpdateSponsorForm"));
	}
	
	@WithMockUser(value = "admin")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
	mockMvc.perform(post("/tournaments/{tournamentId}/sponsors/add", TEST_BEAUTY_ID)
						.with(csrf())
						.param("name", "Sponsor de Prueba")
						.param("money", "7000.00")
						.param("url", "https://www.google.es"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/tournaments/{tournamentId}"));
	}
	
}
