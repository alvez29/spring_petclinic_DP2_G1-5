
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Hability;
import org.springframework.samples.petclinic.service.HabilityService;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class HabilityValidator implements Validator {

	@Autowired
	private HabilityService		habilityService;

	private static final String	REQUIRED	= "required";


	@Override
	public boolean supports(final Class<?> clazz) {
		return Hability.class.isAssignableFrom(clazz);
	}  

	private Boolean noTieneMasDeDosDecimales(final Double num) {
        try {
            Boolean res = false;
            Double n = num * 100;
            if (n % 1 == 0) {
                res = true;
            } else {
                n = n - 0.0000000001;
                if (n % 1 == 0) {
                    res = true;
                }
            }
            return res;
        } catch (NullPointerException npe) {
            return false;
        }
    }

	@Override
	public void validate(final Object obj, final Errors errors) {
		Hability hability = (Hability) obj;
		Double money = hability.getRewardMoney();
		Integer capacity = hability.getCapacity();
		LocalDate date = hability.getDate();
		String name = hability.getName();
		String circuit = hability.getCircuit();
		String status = hability.getStatus();
		Integer id =  hability.getId();
		
		//moneyReward validation
		if (money == null) {
			errors.rejectValue("rewardMoney", "It must be a positive number", "It must be a positive number");
		} else {
			if (!this.noTieneMasDeDosDecimales(money)) {
				errors.rejectValue("rewardMoney", "Invalid money format. It must be a number with no more of two decimals digits", "Invalid money format. It must be a number with no more of two decimals digits");
			}

			if (money < 1) {
				errors.rejectValue("rewardMoney", "Invalid money format. It must be a positive number", "Invalid money format. It must be a positive number");
			}
		}

		//capacity
		if (capacity == null) {
			errors.rejectValue("capacity", "You must add the capacity number", "You must add the capacity number");
		} else {
			if (capacity < 0) {
				errors.rejectValue("capacity", "Capacity must be a positive number", "Capacity must be a positive number");
			}
		}

		//Date
		if (date == null) {
			errors.rejectValue("date", "You must add a date for the event", "You must add a date for the event");

		} else {
			if (date.isBefore(LocalDate.now())) {
				errors.rejectValue("date", "The event must be celebrated in the future", "The event must be celebrated in the future");
			}
		}

		//name
		if (!StringUtils.hasLength(name) || name.length() > 50 || name.length() < 3) {
			errors.rejectValue("name", HabilityValidator.REQUIRED + " and between 3 and 50 characters", HabilityValidator.REQUIRED + " and between 3 and 50 characters");
		}

		//circuit
		if (!StringUtils.hasLength(circuit) || circuit.length() > 50 || circuit.length() < 3) {
			errors.rejectValue("circuit", HabilityValidator.REQUIRED + " and between 3 and 50 characters", HabilityValidator.REQUIRED + " and between 3 and 50 characters");
		}
		
		//status
		if(status != null) {
			if(!status.equals("FINISHED") && !status.equals("PENDING") && !status.equals("DRAFT")) {
				errors.rejectValue("status", "This status is not valid", "This status is not valid");
			}
			if(status.equals("FINISHED") && date.isAfter(LocalDate.now())) {
				errors.rejectValue("status", "The event has not been celebrated yet", "The event has not been celebrated yet");
			}			
		}
	}
}
