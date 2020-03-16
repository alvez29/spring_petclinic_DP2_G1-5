
package org.springframework.samples.petclinic.web;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Hability;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.service.HabilityService;
import org.springframework.samples.petclinic.service.exceptions.ReservedDateExeception;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HabilityController {

	@Autowired
	private HabilityService		habilityService;

	private static final String	VIEWS_HABILITY_CREATE_OR_UPDATE_FORM	= "tournaments/createOrUpdateHabilityForm";


	@InitBinder("hability")
	public void initHabilityBinder(final WebDataBinder dataBinder) {
		dataBinder.setValidator(new HabilityValidator());
	}

	@Autowired
	public HabilityController(final HabilityService habilityService) {
		this.habilityService = habilityService;
	}

	@ModelAttribute("types")
	public Collection<PetType> populatePetTypes() {
		return this.habilityService.getAllTypes();

	}

	@GetMapping("/hability/new")
	public String initCreationForm(final ModelMap model) {
		Hability hability = new Hability();
		model.put("hability", hability);
		return HabilityController.VIEWS_HABILITY_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/hability/new")
	public String processCreationForm(@Valid final Hability hability, final BindingResult result, final ModelMap model) {
		if (result.hasErrors()) {
			model.put("hability", hability);
			return HabilityController.VIEWS_HABILITY_CREATE_OR_UPDATE_FORM;
		} else {
			try {
				hability.setStatus("PENDING");
				this.habilityService.saveHability(hability);
			} catch (ReservedDateExeception ex) {
				result.rejectValue("date", "This date is reserved", "This date is reserved");
				return HabilityController.VIEWS_HABILITY_CREATE_OR_UPDATE_FORM;
			}

			return "redirect:/tournaments";
		}
	}

}
