package org.springframework.samples.petclinic.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.web.SponsorValidator;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public class SponsorValidatorTest {

	@Autowired
	private SponsorValidator sponsorValidator = new SponsorValidator();
	
	@Autowired
	private Sponsor sponsor;
	
	@Autowired
	private Errors errors;
	
	@BeforeEach
	private void setUp() {
		this.sponsor = new Sponsor();
		sponsor.setMoney(7000.00);
		sponsor.setName("Test");
		sponsor.setUrl("https://www.google.es");
		
		this.errors = new BeanPropertyBindingResult(this.sponsor, "");
	}
	
	@Test
	void shouldValidateCorrectSponsor() {
		sponsorValidator.validate(this.sponsor, this.errors);
		
		Assertions.assertThat(errors.getErrorCount()).isEqualTo(0);
	}
	
	@Test
	void shouldNotValidateShortName() {
		this.sponsor.setName("x2");
		this.sponsorValidator.validate(this.sponsor, this.errors);
		
		Assertions.assertThat(errors.getFieldError("name").getCode()).isEqualTo("required and between 3 and 30 characters");
	}
	
	@Test
	void shouldValidateShortName() {
		this.sponsor.setName("xx3");
		this.sponsorValidator.validate(this.sponsor, this.errors);
		
		Assertions.assertThat(errors.getErrorCount()).isEqualTo(0);
	}
	
	@Test
	void shouldNotValidateLongName() {
		this.sponsor.setName("xxxxxxxxxxxxxxxxxxxxxxxxxxxxx31");
		this.sponsorValidator.validate(this.sponsor, this.errors);
		
		Assertions.assertThat(errors.getFieldError("name").getCode()).isEqualTo("required and between 3 and 30 characters");
	}
	
	@Test
	void shouldValidateLongName() {
		this.sponsor.setName("xxxxxxxxxxxxxxxxxxxxxxxxxxxx30");
		this.sponsorValidator.validate(this.sponsor, this.errors);
		
		Assertions.assertThat(errors.getErrorCount()).isEqualTo(0);
	}
	
	@Test
	void shouldNotValidateShortURL() {
		this.sponsor.setUrl("op.gg");
		this.sponsorValidator.validate(this.sponsor, this.errors);
		
		Assertions.assertThat(errors.getFieldError("url").getCode()).isEqualTo("required and must conatain more than 9 characters");
	}
	
	@Test
	void shouldValidateShortURL() {
		this.sponsor.setUrl("https://op.gg");
		this.sponsorValidator.validate(this.sponsor, this.errors);
		
		Assertions.assertThat(errors.getErrorCount()).isEqualTo(0);
	}
	
	@Test
	void shouldNotValidateNegativeMoney() {
		this.sponsor.setMoney(-1000.01);;
		this.sponsorValidator.validate(this.sponsor, this.errors);
		
		Assertions.assertThat(errors.getFieldError("money").getCode()).isEqualTo("Invalid money format. It must be a positive number");
	}
	
	@Test
	void shouldNotValidateMore2DecimalsMoney() {
		this.sponsor.setMoney(1000.001);
		this.sponsorValidator.validate(this.sponsor, this.errors);
		
		Assertions.assertThat(errors.getFieldError("money").getCode()).isEqualTo("Invalid money format. It must be a number with no more of two decimals digits");
	}
	
	@Test
	void shouldNotValidateNullMoney() {
		this.sponsor.setMoney(null);
		this.sponsorValidator.validate(this.sponsor, this.errors);
		
		Assertions.assertThat(errors.getFieldError("money").getCode()).isEqualTo("It must be a positive number");
	}
}
