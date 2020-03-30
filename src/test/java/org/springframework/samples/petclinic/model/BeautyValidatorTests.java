
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.web.BeautyValidator;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

class BeautyValidatorTests {

	@Autowired
	private BeautyValidator	beautyValidator	= new BeautyValidator();

	@Autowired
	private Errors			errors;

	@Autowired
	private Beauty			beauty;


	@BeforeEach
	private void setUp() {
		this.beauty = new Beauty();
		this.beauty.setId(5);
		this.beauty.setName("TestingName");
		this.beauty.setCapacity(10);
		this.beauty.setDate(LocalDate.of(2020, 03, 23));
		this.beauty.setPlace("place");
		this.beauty.setRewardMoney(800.00);
		this.beauty.setStatus("PENDING");

		this.errors = new BeanPropertyBindingResult(this.beauty, "");
	}

	@Test
	void shouldNotValidateCorrectHability() {
		this.beautyValidator.validate(this.beauty, this.errors);
		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(0);

	}

	@Test
	void shouldNotValidateShortName() {
		this.beauty.setName("xx");
		this.beautyValidator.validate(this.beauty, this.errors);

		Assertions.assertThat(this.errors.getFieldError("name").getCode()).isEqualTo("required and between 3 and 30 characters");

	}

	@Test
	void shouldNotValidateLongName() {
		this.beauty.setName("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxv");
		this.beautyValidator.validate(this.beauty, this.errors);

		Assertions.assertThat(this.errors.getFieldError("name").getCode()).isEqualTo("required and between 3 and 30 characters");

	}

	@Test
	void shouldValidate3CharName() {
		this.beauty.setName("xxx");
		this.beautyValidator.validate(this.beauty, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(0);

	}

	@Test
	void shouldValidate30CharName() {
		this.beauty.setName("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		this.beautyValidator.validate(this.beauty, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(0);

	}

	@Test
	void shouldNotValidateNullMoney() {
		this.beauty.setRewardMoney(null);
		this.beautyValidator.validate(this.beauty, this.errors);

		Assertions.assertThat(this.errors.getFieldError("rewardMoney").getCode()).isEqualTo("It must be a positive number");
	}

	@Test
	void shouldNotValidateNegativeNumber() {
		this.beauty.setRewardMoney(-1000.00);
		this.beautyValidator.validate(this.beauty, this.errors);

		Assertions.assertThat(this.errors.getFieldError("rewardMoney").getCode()).isEqualTo("Invalid money format. It must be a positive number");

	}

	@Test
	void shouldNotValidateMoneyWithMoreThan2DecimalDigits() {
		this.beauty.setRewardMoney(1000.001);
		this.beautyValidator.validate(this.beauty, this.errors);

		Assertions.assertThat(this.errors.getFieldError("rewardMoney").getCode()).isEqualTo("Invalid money format. It must be a number with no more of two decimals digits");

	}

	@Test
	void shouldNotValidateNullCapacity() {
		this.beauty.setCapacity(null);
		this.beautyValidator.validate(this.beauty, this.errors);

		Assertions.assertThat(this.errors.getFieldError("capacity").getCode()).isEqualTo("You must add a capacity for the event");

	}

	@Test
	void shouldNotValidateNegativeCapacity() {
		this.beauty.setCapacity(-800);
		this.beautyValidator.validate(this.beauty, this.errors);

		Assertions.assertThat(this.errors.getFieldError("capacity").getCode()).isEqualTo("Capacity must be a positive number");

	}

	@Test
	void shouldNotValidateNullDate() {

		this.beauty.setDate(null);
		this.beautyValidator.validate(this.beauty, this.errors);

		Assertions.assertThat(this.errors.getFieldError("date").getCode()).isEqualTo("You must add a date for the event");
	}

	@Test
	void shouldNotValidatePastDate() {

		this.beauty.setDate(LocalDate.of(2020, 3, 16));
		this.beautyValidator.validate(this.beauty, this.errors);

		Assertions.assertThat(this.errors.getFieldError("date").getCode()).isEqualTo("The event must be celebrated in the future");
	}

	@Test
	void shouldNotValidateShortPlace() {
		this.beauty.setPlace("xx");
		this.beautyValidator.validate(this.beauty, this.errors);

		Assertions.assertThat(this.errors.getFieldError("place").getCode()).isEqualTo("required and between 3 and 50 characters");

	}

	@Test
	void shouldNotValidateLongPlace() {
		this.beauty.setPlace("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		this.beautyValidator.validate(this.beauty, this.errors);

		Assertions.assertThat(this.errors.getFieldError("place").getCode()).isEqualTo("required and between 3 and 50 characters");

	}

	@Test
	void shouldValidate3CharPlace() {
		this.beauty.setPlace("xxx");
		this.beautyValidator.validate(this.beauty, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(0);

	}

	@Test
	void shouldValidate50CharPlace() {
		this.beauty.setPlace("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		this.beautyValidator.validate(this.beauty, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(0);

	}

}
