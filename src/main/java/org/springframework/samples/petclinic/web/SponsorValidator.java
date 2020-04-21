package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Sponsor;
import org.springframework.samples.petclinic.service.SponsorService;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class SponsorValidator implements Validator{

	@Autowired
	private SponsorService sponsorService;
	
	private static final String REQUIRED = "required";
	
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
	public boolean supports(Class<?> clazz) {
		return Sponsor.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Sponsor sponsor = (Sponsor) obj;
		Double money = sponsor.getMoney();
		String name = sponsor.getName();
		String url = sponsor.getUrl();
		
		if (money == null) {
			errors.rejectValue("money", "It must be a positive number", "It must be a positive number");
		} else {
			if (!noTieneMasDeDosDecimales(money)) {
				errors.rejectValue("money",
						"Invalid money format. It must be a number with no more of two decimals digits",
						"Invalid money format. It must be a number with no more of two decimals digits");
			}

			if (money < 1) {
				errors.rejectValue("money", "Invalid money format. It must be a positive number",
						"Invalid money format. It must be a positive number");
			}
		}
		
		if (!StringUtils.hasLength(name) || name.length() > 30 || name.length() < 3) {
			errors.rejectValue("name", REQUIRED + " and between 3 and 30 characters",
					REQUIRED + " and between 3 and 30 characters");
		}

		if (!StringUtils.hasLength(url) || url.length() < 9) {
			errors.rejectValue("url", REQUIRED + " and must conatain more than 9 characters",
					REQUIRED + " and must conatain more than 9 characters");
		}

	}
	
}
