package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Hability;
import org.springframework.samples.petclinic.service.HabilityService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;

@WebMvcTest(value = HabilityController.class,
includeFilters = @ComponentScan.Filter(value = PetTypeFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class HabilityControllerTests {
	
	@Autowired
	private HabilityController habilityController;
	
	@Autowired
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
}
