package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Race;
import org.springframework.samples.petclinic.model.ResultTime;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.RaceResultService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(value = RaceResultController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class RaceResultControllerTests {
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private RaceResultService raceResultService;
	
	@MockBean
	private PetService petService;
	
	@MockBean
	private TournamentService tournamentService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private ResultTime result;
	
	private Race race;
	
	private Pet pet1;
	
	private Pet pet2;
	
	private PetType petType;

	private static final int TEST_RACE_RESULT_ID = 1;
	
	private static final int TEST_RACE_ID = 1;

	private static final int TEST_PET1_ID = 1;
	
	private static final int TEST_PET2_ID = 2;


	
	@BeforeEach
	void setup() {

		this.result = new ResultTime();
		result.setId(TEST_RACE_RESULT_ID);
		result.setTime(40.0);
		
		this.pet1 = new Pet();
		pet1.setId(TEST_PET1_ID);
		pet1.setBirthDate(LocalDate.of(2020, 1, 1));
		pet1.setName("Test");
		
		this.petType = new PetType();
		petType.setId(1);
		petType.setName("Example");
		pet1.setType(petType);
		result.setPet(pet1);
		
		this.pet2 = new Pet();
		pet2.setId(TEST_PET2_ID);
		pet2.setBirthDate(LocalDate.of(2020, 1, 1));
		pet2.setName("Test");
		pet2.setType(petType);

		
		this.race = new Race();
		race.setId(1);
		race.setName("Testing");
		race.setCapacity(10);
		race.setCanodrome("Canodrome");
		race.setDate(LocalDate.of(2020, 1, 10));
		race.setRewardMoney(1000.00);
		race.setStatus("DRAFT");
		List<Pet> participants = new ArrayList<Pet>();
		participants.add(pet1);
		participants.add(pet2);
		race.setPets(participants);
		result.setTournament(race);
		
		List<ResultTime> results = new ArrayList<ResultTime>();
		results.add(result);
		
		given(this.raceResultService.isInTournament(1, 2)).willReturn(true);
		given(this.raceResultService.isInTournament(1, 3)).willReturn(false);
		given(this.raceResultService.findByTournamnetId(TEST_RACE_ID)).willReturn(results);
		given(this.petService.findPetById(TEST_PET2_ID)).willReturn(pet2);
		given(tournamentService.findTournamentById(1)).willReturn(race);

	}
	
	@WithMockUser(value = "admin")
	@Test
	void raceResultList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/tournaments/race/{raceId}/result", RaceResultControllerTests.TEST_RACE_ID))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("results"))
		.andExpect(MockMvcResultMatchers.view().name("tournaments/raceResultList"));
	}

	@WithMockUser(value = "admin")
	@Test
	void testInitCreationFormSuccess() throws Exception {
		mockMvc.perform(get("/tournament/race/{raceId}/pet/{petId}/add_result", RaceResultControllerTests.TEST_RACE_ID, RaceResultControllerTests.TEST_PET2_ID)).andExpect(status().isOk()).andExpect(model().attributeExists("resultTime"))
				.andExpect(view().name("tournaments/createOrUpdateRaceResultForm"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testInitCreationFormHasErrors() throws Exception {
		mockMvc.perform(get("/tournament/race/{raceId}/pet/3/add_result", RaceResultControllerTests.TEST_RACE_ID)).andExpect(status().is3xxRedirection()).andExpect(model().attributeDoesNotExist("resultTime"))
				.andExpect(view().name("redirect:/oups"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/tournament/race/{tournamentId}/pet/{petId}/add_result", RaceResultControllerTests.TEST_RACE_ID, RaceResultControllerTests.TEST_PET2_ID)
				.with(csrf())
				.param("time", "40.057"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/tournaments/1"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/tournament/race/{tournamentId}/pet/{petId}/add_result", RaceResultControllerTests.TEST_RACE_ID, RaceResultControllerTests.TEST_PET2_ID)
				.with(csrf())
				.param("time", "-40.057"))
		.andExpect(status().isOk())
		.andExpect(view().name("tournaments/createOrUpdateRaceResultForm"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testDelete() throws Exception {
		mockMvc.perform(get("/tournaments/race/{tournamentId}/result/{resultId}/delete", RaceResultControllerTests.TEST_RACE_ID, RaceResultControllerTests.TEST_RACE_RESULT_ID))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/tournaments/race/1/result"));
	}
}
