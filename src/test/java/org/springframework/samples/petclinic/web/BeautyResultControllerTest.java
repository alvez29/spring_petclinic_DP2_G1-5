package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Beauty;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.ResultScore;
import org.springframework.samples.petclinic.service.BeautyResultService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = BeautyResultController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class BeautyResultControllerTest {

	private static final Object TEST_BEAUTY_ID = 1;


	@MockBean
	private BeautyResultService beautyResultService;
	
	@MockBean
	private PetService petService;
	
	@MockBean
	private TournamentService tournamentService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setUp() {
		
		Beauty beauty = new Beauty();
		beauty.setId(1);
		beauty.setName("TestingName");
		beauty.setCapacity(10);
		beauty.setDate(LocalDate.of(2020, 12, 23));
		beauty.setPlace("place");
		beauty.setRewardMoney(800.00);
		beauty.setStatus("DRAFT");
		
		Pet pet = new Pet();
		pet.setId(1);
		pet.setBirthDate(LocalDate.now().minusDays(2));
		pet.setName("TestingPet");
		PetType petType = new PetType();
		petType.setId(1);
		petType.setName("TestingName");
		pet.setType(petType);
		
		Pet pet2 = new Pet();
		pet.setId(1);
		pet.setBirthDate(LocalDate.now().minusDays(2));
		pet.setName("TestingPet");
		petType.setId(2);
		petType.setName("TestingName");
		pet.setType(petType);
		
		beauty.setPets(Arrays.asList(pet,pet2));
		
		ResultScore resultScore = new ResultScore();
		resultScore.setHaircut(10);
		resultScore.setHaircutdif(10);
		resultScore.setId(1);
		resultScore.setPet(pet);
		resultScore.setPosture(10);
		resultScore.setTechnique(10);
		resultScore.setTournament(beauty);
	
		
		given(this.beautyResultService.findByTournamentId(1)).willReturn(Arrays.asList(resultScore));
		given(this.beautyResultService.isInTournament(1, 1)).willReturn(true);
		given(this.beautyResultService.isInTournament(1, 3)).willReturn(false);
		given(this.petService.findPetById(2)).willReturn(pet2);
		
	}
	
	@WithMockUser(value = "admin")
	@Test
	void beautyResultList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/tournaments/beauty/{tournamentId}/result", BeautyResultControllerTest.TEST_BEAUTY_ID))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("beautyResults"))
		.andExpect(MockMvcResultMatchers.view().name("tournaments/beautyResultList"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testInitCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/tournament/beauty/{tournamentId}/pet/{petId}/add_result", BeautyResultControllerTest.TEST_BEAUTY_ID, 1))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("resultScore"))
		.andExpect(MockMvcResultMatchers.view().name("/tournaments/createOrUpdateBeautyResultForm"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testInitCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/tournament/beauty/{tournamentId}/pet/{petId}/add_result", BeautyResultControllerTest.TEST_BEAUTY_ID, 3))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
		.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("resultScore"))
		.andExpect(MockMvcResultMatchers.view().name("redirect:/oups"));
	}

	@WithMockUser(value = "admin")
	@Test
	void testProcessCreateFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/tournament/beauty/{tournamentId}/pet/{petId}/add_result", BeautyResultControllerTest.TEST_BEAUTY_ID, 2)
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("haircut", "10")
				.param("haircutdif", "10")
				.param("technique", "10")
				.param("posture", "10"))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
		.andExpect(MockMvcResultMatchers.view().name("redirect:/tournaments/1"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testProcessCreateFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/tournament/beauty/{tournamentId}/pet/{petId}/add_result", BeautyResultControllerTest.TEST_BEAUTY_ID, 2)
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
		this.mockMvc.perform(MockMvcRequestBuilders.get("/tournaments/beauty/{tournamentId}/result/{beautyResultId}/delete", BeautyResultControllerTest.TEST_BEAUTY_ID, 1))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
		.andExpect(MockMvcResultMatchers.view().name("redirect:/tournaments/beauty/{tournamentId}/result"));
	}
	
}
