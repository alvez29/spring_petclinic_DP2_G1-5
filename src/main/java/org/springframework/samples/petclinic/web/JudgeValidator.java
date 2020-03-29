package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.service.JudgeService;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class JudgeValidator implements Validator {

	@Autowired
	private JudgeService judgeService;

	private static final String	REQUIRED = "required";
	
	@Override
	public boolean supports(final Class<?> clazz) {
		return Judge.class.isAssignableFrom(clazz);
	}

	
	@Override
	public void validate(Object target, Errors errors) {
		Judge judge = (Judge) target;
		String firstName = judge.getFirstName();
		
		if (!StringUtils.hasLength(firstName) || firstName.length()>30 || firstName.length()<3) {
			errors.rejectValue("firstName", REQUIRED+" and between 3 and 30 characters", REQUIRED+" and between 3 and 30 character");
		}
		
		String lastName = judge.getLastName();
		if (!StringUtils.hasLength(lastName) || lastName.length()>30 || lastName.length()<3) {
			errors.rejectValue("lastName", REQUIRED+" and between 3 and 30 characters", REQUIRED+" and between 3 and 30 character");
		}
		
		String contact = judge.getContact();
		if (!StringUtils.hasLength(contact) || contact.length()>144 || contact.length()<3) {
			errors.rejectValue("contact", REQUIRED+" and between 3 and 144 characters", REQUIRED+" and between 3 and 144 character");
		}
		
		String city = judge.getCity();
		if (!StringUtils.hasLength(city) || city.length()>64 || city.length()<3) {
			errors.rejectValue("city", REQUIRED+" and between 3 and 64 characters", REQUIRED+" and between 3 and 64 character");
	
		}
	}	
}
