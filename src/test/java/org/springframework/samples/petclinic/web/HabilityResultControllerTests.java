
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Hability;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.ResultTime;
import org.springframework.samples.petclinic.service.HabilityResultService;
import org.springframework.samples.petclinic.service.HabilityService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(value = HabilityResultController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class HabilityResultControllerTests {

	@MockBean
	private UserService				userService;

	@MockBean
	private HabilityService			habilityService;

	@MockBean
	private PetService				petService;

	@MockBean
	private TournamentService		tournamentService;

	@MockBean
	private HabilityResultService	habilityResultService;

	private static final int		TEST_HABILITY_ID	= 1;

	@Autowired
	private MockMvc					mockMvc;


	@BeforeEach
	void setup() {

		Hability hability = new Hability();
		hability.setId(HabilityResultControllerTests.TEST_HABILITY_ID);
		hability.setName("Testing");
		hability.setCapacity(10);
		hability.setCircuit("Circuit");
		hability.setDate(LocalDate.of(2020, 12, 1));
		hability.setRewardMoney(1000.00);
		hability.setStatus("DRAFT");
		List<Pet> pets = new ArrayList<>();

		Pet pet1 = new Pet();
		Pet pet2 = new Pet();
		PetType petType = new PetType();
		pet1.setName("Pepe");
		pet1.setBirthDate(LocalDate.of(2010, 3, 1));
		pet1.setId(1);
		pet1.setType(petType);
		pet2.setName("Pepote");
		pet2.setBirthDate(LocalDate.of(2010, 3, 1));
		pet2.setId(2);
		pet2.setType(petType);

		pets.add(pet1);
		pets.add(pet2);
		hability.setPets(pets);

		ResultTime result = new ResultTime();
		result.setId(HabilityResultControllerTests.TEST_HABILITY_ID);
		result.setLowFails(1);
		result.setMediumFails(1);
		result.setBigFails(1);
		result.setTournament(hability);
		result.setPet(pet1);

		List<ResultTime> results = new ArrayList<>();
		results.add(result);

		BDDMockito.given(this.habilityResultService.isInTournament(1, 1)).willReturn(true);

		BDDMockito.given(this.habilityResultService.isInTournament(1, 3)).willReturn(false);

		BDDMockito.given(this.habilityResultService.findByTournamentId(HabilityResultControllerTests.TEST_HABILITY_ID)).willReturn(results);
	}

	@WithMockUser(value = "admin")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/tournament/hability/1/pet/1/add_result")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("resultTime"))
			.andExpect(MockMvcResultMatchers.view().name("tournaments/createOrUpdateHabilityResultForm"));
	}

	@WithMockUser(value = "admin")
	@Test
	void testInitNoSuccesCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/tournament/hability/1/pet/3/add_result")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("resultTime"))
			.andExpect(MockMvcResultMatchers.view().name("redirect:/oups"));
	}

	@WithMockUser(value = "admin")
	@Test
	void habilityResultList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/tournaments/hability/{habilityId}/result", HabilityResultControllerTests.TEST_HABILITY_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("results")).andExpect(MockMvcResultMatchers.view().name("tournaments/habilityResultList"));
	}

	@WithMockUser(value = "admin")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/tournament/hability/1/pet/2/add_result").param("time", "40.").param("lowFails", "1").with(SecurityMockMvcRequestPostProcessors.csrf()).param("mediumFails", "1").param("bigFails", "1"))

			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/tournaments/1"));
	}

	@WithMockUser(value = "admin")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/tournament/hability/1/pet/2/add_result").param("time", "-40.").param("lowFails", "1").with(SecurityMockMvcRequestPostProcessors.csrf()).param("mediumFails", "1").param("bigFails", "1"))

			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("tournaments/createOrUpdateHabilityResultForm"));
	}

	@WithMockUser(value = "admin")
	@Test
	void testDelete() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/tournaments/hability/1/result/1/delete")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/tournaments/hability/1/result"));
	}

}
