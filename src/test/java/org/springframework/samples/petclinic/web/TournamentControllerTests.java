package org.springframework.samples.petclinic.web;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Sponsor;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.JudgeService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.samples.petclinic.web.TounamentController;

@WebMvcTest(controllers=TounamentController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class TournamentControllerTests {

	private static final int TEST_TOURNAMENT_ID = 1;


	private static final int TEST_PET_ID = 1;


	private static final int TEST_JUDGE_ID = 1;
	

	@MockBean
	private TournamentService tournamentService;
        
        @MockBean
	private UserService userService;
        
    @MockBean
    private JudgeService judgeService;
        
    @MockBean
    private PetService petService;
    
    @MockBean
    private AuthoritiesService authoritiesService; 

	@Autowired
	private MockMvc mockMvc;

	private Tournament tournament;
	
	@BeforeEach
	void setUp() {
		tournament = new Tournament();
		tournament.setId(TEST_TOURNAMENT_ID);
		tournament.setCapacity(1000);
		tournament.setDate(LocalDate.of(2040, 12, 1));
		tournament.setName("Tournamnet Test");
		tournament.setRewardMoney(1000.00);
		tournament.setStatus("DRAFT");

		List<Judge> judges = new ArrayList<Judge>();		
		Judge judge1 = new Judge();
		judge1.setId(1);
		judge1.setFirstName("Pablo");
		judge1.setLastName("Unknow");
		judge1.setCity("Almeria");
		judge1.setContact("@pablounknow");
		judges.add(judge1);
		tournament.setJudges(judges);
		
		List<Pet> pets = new ArrayList<Pet>();
		Pet pet1 = new Pet();
		pet1.setId(1);
		pet1.setName("Perro");
		pet1.setBirthDate(LocalDate.of(2010, 12, 12));
		PetType pt = new PetType();
		pt.setId(1);
		pt.setName("Beagle");
		pet1.setType(pt);
		pets.add(pet1);
		tournament.setPets(pets);
		
		Visit visit = new Visit();
		//visit.setDate("");
		
		
		
		List<Sponsor> sponsors = new ArrayList<Sponsor>();
		//TODO
		tournament.setSponsors(sponsors);
		
		
		given(this.tournamentService.findTournamentById(TEST_TOURNAMENT_ID)).willReturn(tournament);
		given(this.petService.findPetById(TEST_PET_ID)).willReturn(pet1);
		given(this.judgeService.findJudgeById(TEST_PET_ID)).willReturn(judge1);
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testShowTournament() throws Exception {
		mockMvc.perform(get("/tournaments/{tournamentId}", TEST_TOURNAMENT_ID)).andExpect(status().isOk())
				.andExpect(model().attribute("tournament", hasProperty("capacity", is(1000))))
				.andExpect(model().attribute("tournament", hasProperty("date", is(LocalDate.of(2040, 12, 1)))))
				.andExpect(model().attribute("tournament", hasProperty("name", is("Tournamnet Test"))))
				.andExpect(model().attribute("tournament", hasProperty("rewardMoney", is(1000.00))))
				.andExpect(model().attribute("tournament", hasProperty("status", is("DRAFT"))))
				.andExpect(view().name("tournaments/tournamentDetails"));
	}
	
		
	@WithMockUser(value = "admin")
	@Test
	void testTournamnetsList() throws Exception {
		mockMvc.perform(get("/tournaments")).andExpect(status().isOk()).andExpect(model().attributeExists("tournaments"))
				.andExpect(view().name("tournaments/tournamentList"));
	}
		
	@WithMockUser(value = "admin")
	@Test
	void shouldAddPet() throws Exception{
		mockMvc.perform(get("/tournaments/{tournamentId}/addpet/{petId}", TEST_TOURNAMENT_ID, TEST_PET_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/tournaments/1"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void shouldAddJudge() throws Exception {
		mockMvc.perform(get("/tournaments/{tournamentId}/addjudge/{judgeId}", TEST_TOURNAMENT_ID, TEST_JUDGE_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/tournaments/1"));
	}
	
}
