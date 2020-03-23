package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.web.RaceValidator;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public class RaceValidatorTest {

	
	@Autowired
	private RaceValidator raceValidator = new RaceValidator();
	
	@Autowired
	private Race race;
	
	@Autowired
	private Errors errors;
	
	@BeforeEach
	private void setUp() {
		this.race = new Race();
		race.setName("Testing");
		race.setCapacity(10);
		race.setCanodrome("Canodrome");
		race.setDate(LocalDate.of(2020, 12, 1));
		race.setRewardMoney(1000.00);
		race.setStatus("DRAFT");	
		
		this.errors =  new BeanPropertyBindingResult(this.race, "");
	}
	
	@Test
	void shoulValidateCorrectRace() {
		raceValidator.validate(this.race, this.errors);
		
		assertThat(errors.getErrorCount()).isEqualTo(0);
	}
	
	@Test
	void shouldNotValidateShortName() {
		this.race.setName("NN");
		this.raceValidator.validate(this.race, this.errors);
			
		assertThat(errors.getFieldError("name").getCode()).isEqualTo("required and between 3 and 30 characters");
	}
	
	@Test
	void shouldValidate3CharName() {
		this.race.setName("NNN");
		this.raceValidator.validate(this.race, this.errors);
			
		assertThat(errors.getErrorCount()).isEqualTo(0);
	}
	
	@Test
	void shouldNotValidateLongName() {
		this.race.setName("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX31");
		raceValidator.validate(this.race, this.errors);
			
			assertThat(errors.getFieldError("name").getCode()).isEqualTo("required and between 3 and 30 characters");
	}
	
	@Test
	void shouldValidate30CharName() {
		this.race.setName("XXXXXXXXXXXXXXXXXXXXXXXXXXXX30");
		raceValidator.validate(this.race, this.errors);
			
		assertThat(errors.getErrorCount()).isEqualTo(0);
	}

	@Test
	void shouldNotValidateNegativeMoney() {
		this.race.setRewardMoney(-1000.00);
		raceValidator.validate(this.race, this.errors);
			
			assertThat(errors.getFieldError("rewardMoney").getCode()).isEqualTo("Invalid money format. It must be a positive number");
	}
	
	
	@Test
	void shouldNotValidateNullMoney() {
		this.race.setRewardMoney(null);
		raceValidator.validate(this.race, this.errors);
	
		assertThat(errors.getFieldError("rewardMoney").getCode()).isEqualTo("It must be a positive number");
	}
	
	@Test
	void shouldNotValidateMoneyMoreThan2DecimalDigits() {
		this.race.setRewardMoney(1000.002);
		raceValidator.validate(this.race, this.errors);
	
		assertThat(errors.getFieldError("rewardMoney").getCode()).isEqualTo("Invalid money format. It must be a number with no more of two decimals digits");
	}
	
	@Test
	void shouldNotValidateCapacityNull() {
		this.race.setCapacity(null);
		raceValidator.validate(this.race, this.errors);
	
		assertThat(errors.getFieldError("capacity").getCode()).isEqualTo("You must add a capacity for the event");
	}
	
	@Test
	void shouldNotValidateCapacityNegative() {
		this.race.setCapacity(-10);
		raceValidator.validate(this.race, this.errors);
	
		assertThat(errors.getFieldError("capacity").getCode()).isEqualTo("Capacity must be a positive number");
		}
	
	@Test
	void shouldNotValidateDateNull() {
		this.race.setDate(null);
		raceValidator.validate(this.race, this.errors);
	
		assertThat(errors.getFieldError("date").getCode()).isEqualTo("You must add a date for the event");
		}
	
	@Test
	void shouldNotValidateDatePast() {
		this.race.setDate(LocalDate.now().minusDays(1));
		raceValidator.validate(this.race, this.errors);
	
		assertThat(errors.getFieldError("date").getCode()).isEqualTo("The event must be celebrated in the future");
		}
	
	@Test
	void shouldNotValidateShortCanodrome() {
		this.race.setCanodrome("NN");
		this.raceValidator.validate(this.race, this.errors);
			
		assertThat(errors.getFieldError("canodrome").getCode()).isEqualTo("required and between 3 and 50 characters");
	}
	
	@Test
	void shouldValidate3CharCanodrome() {
		this.race.setCanodrome("NNN");
		this.raceValidator.validate(this.race, this.errors);
			
		assertThat(errors.getErrorCount()).isEqualTo(0);
	}
	
	@Test
	void shouldNotValidateLongCanodrome() {
		this.race.setCanodrome("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX51");
		raceValidator.validate(this.race, this.errors);
			
			assertThat(errors.getFieldError("canodrome").getCode()).isEqualTo("required and between 3 and 50 characters");
	}
	
	@Test
	void shouldValidate50CharCanodrome() {
		this.race.setCanodrome("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX50");
		raceValidator.validate(this.race, this.errors);
			
		assertThat(errors.getErrorCount()).isEqualTo(0);
	}

	
}