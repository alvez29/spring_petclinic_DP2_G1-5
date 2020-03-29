package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import org.springframework.samples.petclinic.model.Beauty;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BeautyValidator implements Validator {

	private static final String REQUIRED = "required";


	@Override
	public boolean supports(final Class<?> clazz) {
		return Beauty.class.isAssignableFrom(clazz);
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
		Beauty beauty = (Beauty) obj;
		Double money = beauty.getRewardMoney();
		Integer capacity = beauty.getCapacity();
		LocalDate date = beauty.getDate();
		String name = beauty.getName();
		String place = beauty.getPlace();
		String status = beauty.getStatus();
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
			errors.rejectValue("capacity", "You must add a capacity for the event", "You must add a capacity for the event");
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
		if (!StringUtils.hasLength(name) || name.length() > 30 || name.length() < 3) {
			errors.rejectValue("name", BeautyValidator.REQUIRED + " and between 3 and 30 characters", BeautyValidator.REQUIRED + " and between 3 and 30 characters");
		}

		//place
		if (!StringUtils.hasLength(place) || place.length() > 50 || place.length() < 3) {
			errors.rejectValue("place", BeautyValidator.REQUIRED + " and between 3 and 50 characters", BeautyValidator.REQUIRED + " and between 3 and 50 characters");
		}

		//status
		if (status != null) {
			if (status.equals("FINISHED") && date.isAfter(LocalDate.now())) {
				errors.rejectValue("status", "The event has not been celebrated yet", "The event has not been celebrated yet");
			}

			if (!status.equals("FINISHED") && !status.equals("PENDING") && !status.equals("DRAFT")) {
				errors.rejectValue("status", "This status is not valid", "This status is not valid");
			}
		}
	}

}