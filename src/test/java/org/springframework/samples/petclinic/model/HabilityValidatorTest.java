package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.web.HabilityValidator;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

class HabilityValidatorTest {
	
	@Autowired
	private HabilityValidator habilityValidator = new HabilityValidator();
	
	@Autowired
	private Hability hability;
	
	@Autowired
	private Errors errors;
	
	@BeforeEach
	private void setUp() {
		this.hability = new Hability();
		hability.setName("Testing");
		hability.setCapacity(10);
		hability.setCircuit("Circuit");
		hability.setDate(LocalDate.of(2020, 12, 1));
		hability.setRewardMoney(1000.00);
		hability.setStatus("PENDING");	
		
		this.errors =  new BeanPropertyBindingResult(this.hability, "");
	}
	
	@Test
	void shoulValidateCorrectHability() {
		habilityValidator.validate(this.hability, this.errors);
		
		assertThat(errors.getErrorCount()).isEqualTo(0);
	}
	
	@Test
	void shouldNotValidateShortName() {
		this.hability.setName("NN");
		this.habilityValidator.validate(this.hability, this.errors);
			
		assertThat(errors.getFieldError("name").getCode()).isEqualTo("required and between 3 and 30 characters");
	}
	
	@Test
	void shouldValidate3CharName() {
		this.hability.setName("NNN");
		this.habilityValidator.validate(this.hability, this.errors);
			
		assertThat(errors.getErrorCount()).isEqualTo(0);
	}
	
	@Test
	void shouldNotValidateLongName() {
		this.hability.setName("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX31");
		habilityValidator.validate(this.hability, this.errors);
			
			assertThat(errors.getFieldError("name").getCode()).isEqualTo("required and between 3 and 30 characters");
	}
	
	@Test
	void shouldValidate30CharName() {
		this.hability.setName("XXXXXXXXXXXXXXXXXXXXXXXXXXXX30");
		habilityValidator.validate(this.hability, this.errors);
			
		assertThat(errors.getErrorCount()).isEqualTo(0);
	}
	
	@Test
	void shouldNotValidateNullMoney() {
		this.hability.setRewardMoney(null);
		habilityValidator.validate(this.hability, this.errors);
			
			assertThat(errors.getFieldError("rewardMoney").getCode()).isEqualTo("It must be a positive number");
	}
	

	@Test
	void shouldNotValidateNegativeMoney() {
		this.hability.setRewardMoney(-1000.00);
		habilityValidator.validate(this.hability, this.errors);
			
			assertThat(errors.getFieldError("rewardMoney").getCode()).isEqualTo("Invalid money format. It must be a positive number");
	}
	
	@Test
	void shouldNotValidateMoneyWithMoreThan2DecimalsDigits() {
		this.hability.setRewardMoney(1000.001);
		habilityValidator.validate(this.hability, this.errors);
			
			assertThat(errors.getFieldError("rewardMoney").getCode()).isEqualTo("Invalid money format. It must be a number with no more of two decimals digits");
	}
	
	@Test
	void shouldNotValidateNullCapacity() {
		this.hability.setCapacity(null);
		habilityValidator.validate(this.hability, this.errors);
			
			assertThat(errors.getFieldError("capacity").getCode()).isEqualTo("You must add the capacity number");
	}
	
	@Test
	void shouldNotValidateNegativeCapacity() {
		this.hability.setCapacity(-10);
		habilityValidator.validate(this.hability, this.errors);
			
			assertThat(errors.getFieldError("capacity").getCode()).isEqualTo("Capacity must be a positive number");
	}
	
	@Test
	void shouldNotValidateNullDate() {
		this.hability.setDate(null);
		habilityValidator.validate(this.hability, this.errors);
			
			assertThat(errors.getFieldError("date").getCode()).isEqualTo("You must add a date for the event");
	}
	
	@Test
	void shouldNotValidatePastDate() {
		this.hability.setDate(LocalDate.of(2020, 3, 16));
		habilityValidator.validate(this.hability, this.errors);
			
			assertThat(errors.getFieldError("date").getCode()).isEqualTo("The event must be celebrated in the future");
	}
	
	@Test
	void shouldNotValidateShortCircuit() {
		this.hability.setCircuit("NN");
		this.habilityValidator.validate(this.hability, this.errors);
			
		assertThat(errors.getFieldError("circuit").getCode()).isEqualTo("required and between 3 and 50 characters");
	}
	
	@Test
	void shouldValidate3Charcircuit() {
		this.hability.setName("NNN");
		this.habilityValidator.validate(this.hability, this.errors);
			
		assertThat(errors.getErrorCount()).isEqualTo(0);
	}
	
	@Test
	void shouldNotValidateLongCircuit() {
		this.hability.setCircuit("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX51");
		habilityValidator.validate(this.hability, this.errors);
			
			assertThat(errors.getFieldError("circuit").getCode()).isEqualTo("required and between 3 and 50 characters");
	}
	
	@Test
	void shouldValidate50CharCircuit() {
		this.hability.setCircuit("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX50");
		habilityValidator.validate(this.hability, this.errors);
			
		assertThat(errors.getErrorCount()).isEqualTo(0);
	}
	
}
