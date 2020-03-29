package org.springframework.samples.petclinic.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.web.JudgeValidator;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public class JudgeValidatorTest {

	@Autowired
	private JudgeValidator judgeValidator = new JudgeValidator();
	
	@Autowired
	private Judge judge;
	
	@Autowired
	private Errors errors;
	
	@BeforeEach
	private void setUp() {
		this.judge = new Judge();
		judge.setFirstName("Paco");
		judge.setLastName("Perez");
		judge.setContact("123123123");
		judge.setCity("Madrid");
		
		this.errors = new BeanPropertyBindingResult(this.judge, "");
	}
	
	@Test
	void shouldValidateCorrectJudge() {
		judgeValidator.validate(this.judge, this.errors);
		
		Assertions.assertThat(errors.getErrorCount()).isEqualTo(0);
	}
	
	
	//FIRST NAME
	
	@Test
	void shouldNotValidateShortName() {
		this.judge.setFirstName("ee");
		this.judgeValidator.validate(this.judge, this.errors);
		
		Assertions.assertThat(errors.getFieldError("firstName").getCode()).isEqualTo("required and between 3 and 30 characters");
	}
	
	
	@Test
	void shouldValidateShortName() {
		this.judge.setFirstName("eee");
		this.judgeValidator.validate(this.judge, this.errors);
		
		Assertions.assertThat(errors.getErrorCount()).isEqualTo(0);
	}
	
	
	@Test
	void shouldNotValidateLongName() {
		this.judge.setFirstName("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		this.judgeValidator.validate(this.judge, this.errors);
		
		Assertions.assertThat(errors.getFieldError("firstName").getCode()).isEqualTo("required and between 3 and 30 characters");
	}


	@Test
	void shouldValidateLongName() {
		this.judge.setFirstName("eeeeeeeeeeeeeeeeeeeeeeeeeeee");
		this.judgeValidator.validate(this.judge, this.errors);
		
		Assertions.assertThat(errors.getErrorCount()).isEqualTo(0);
	}
	
	//LAST NAME
	
	@Test
	void shouldNotValidateShortLastName() {
		this.judge.setLastName("ee");
		this.judgeValidator.validate(this.judge, this.errors);
		
		Assertions.assertThat(errors.getFieldError("lastName").getCode()).isEqualTo("required and between 3 and 30 characters");
	}
	
	
	@Test
	void shouldValidateShortLastName() {
		this.judge.setLastName("eee");
		this.judgeValidator.validate(this.judge, this.errors);
		
		Assertions.assertThat(errors.getErrorCount()).isEqualTo(0);
	}
	
	
	@Test
	void shouldNotValidateLongLastName() {
		this.judge.setLastName("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		this.judgeValidator.validate(this.judge, this.errors);
		
		Assertions.assertThat(errors.getFieldError("lastName").getCode()).isEqualTo("required and between 3 and 30 characters");
	}


	@Test
	void shouldValidateLongLastName() {
		this.judge.setLastName("eeeeeeeeeeeeeeeeeeeeeeeeeeee");
		this.judgeValidator.validate(this.judge, this.errors);
		
		Assertions.assertThat(errors.getErrorCount()).isEqualTo(0);
	}

	//CONTACT
	
	@Test
	void shouldNotValidateShortContact() {
		this.judge.setContact("ee");
		this.judgeValidator.validate(this.judge, this.errors);
		
		Assertions.assertThat(errors.getFieldError("contact").getCode()).isEqualTo("required and between 3 and 144 characters");
	}
	
	
	@Test
	void shouldValidateShortContact() {
		this.judge.setContact("eee");
		this.judgeValidator.validate(this.judge, this.errors);
		
		Assertions.assertThat(errors.getErrorCount()).isEqualTo(0);
	}
	
	
	@Test
	void shouldNotValidateLongContact() {
		this.judge.setContact("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
				+ "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
				+ "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
				+ "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
				+ "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		this.judgeValidator.validate(this.judge, this.errors);
		
		Assertions.assertThat(errors.getFieldError("contact").getCode()).isEqualTo("required and between 3 and 144 characters");
	}


	@Test
	void shouldValidateLongContact() {
		this.judge.setContact("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		this.judgeValidator.validate(this.judge, this.errors);
		
		Assertions.assertThat(errors.getErrorCount()).isEqualTo(0);
	}

	//CITY
	
	@Test
	void shouldNotValidateShortCity() {
		this.judge.setCity("ee");
		this.judgeValidator.validate(this.judge, this.errors);
		
		Assertions.assertThat(errors.getFieldError("city").getCode()).isEqualTo("required and between 3 and 64 characters");
	}
	
	
	@Test
	void shouldValidateShortCity() {
		this.judge.setCity("eee");
		this.judgeValidator.validate(this.judge, this.errors);
		
		Assertions.assertThat(errors.getErrorCount()).isEqualTo(0);
	}
	
	
	@Test
	void shouldNotValidateLongCity() {
		this.judge.setCity("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
				+ "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
				+ "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		this.judgeValidator.validate(this.judge, this.errors);
		
		Assertions.assertThat(errors.getFieldError("city").getCode()).isEqualTo("required and between 3 and 64 characters");
	}


	@Test
	void shouldValidateLongCity() {
		this.judge.setCity("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		this.judgeValidator.validate(this.judge, this.errors);
		
		Assertions.assertThat(errors.getErrorCount()).isEqualTo(0);
	}

	
	
	
}
