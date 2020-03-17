package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.service.JudgeService;
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
		//Judge judge = (Judge) target;
		
	}
	
	
	

}
