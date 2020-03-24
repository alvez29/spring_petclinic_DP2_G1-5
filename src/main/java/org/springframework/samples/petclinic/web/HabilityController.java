
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Hability;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.service.HabilityService;
import org.springframework.samples.petclinic.service.exceptions.ReservedDateExeception;
import org.springframework.samples.petclinic.service.exceptions.SponsorAmountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HabilityController {

	@Autowired
	private HabilityService habilityService;

	private static final String VIEWS_HABILITY_CREATE_OR_UPDATE_FORM = "tournaments/createOrUpdateHabilityForm";

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
	
	@ModelAttribute("statusTypes")
	public Collection<String> getStatusTypes(){
		List<String> statusType = new ArrayList<String>();
		statusType.add("PENDING");
		statusType.add("FINISHED");
		statusType.add("DRAFT");
		return statusType;
	}

	@GetMapping("/hability/new")
	public String initCreationForm(final ModelMap model) {
		Hability hability = new Hability();
		model.put("hability", hability);
		return HabilityController.VIEWS_HABILITY_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/hability/new")
	public String processCreationForm(@Valid final Hability hability, final BindingResult result,
			final ModelMap model) {
		if (result.hasErrors()) {
			model.put("hability", hability);
			return HabilityController.VIEWS_HABILITY_CREATE_OR_UPDATE_FORM;
		} else {
			try {
				hability.setStatus("DRAFT");
				this.habilityService.saveHability(hability);
			} catch (ReservedDateExeception ex) {
				result.rejectValue("date", "This date is reserved", "This date is reserved");
				return HabilityController.VIEWS_HABILITY_CREATE_OR_UPDATE_FORM;
			}

			return "redirect:/tournaments";
		}
	}

	@GetMapping(value = "tournaments/hability/{habilityId}/edit")
	public String initUpdateForm(@PathVariable("habilityId") int habilityId, ModelMap model) {
		Hability hability = this.habilityService.findHabilityById(habilityId);
		boolean edit = true;
		model.put("hability", hability);
		model.put("edit", edit);
		return VIEWS_HABILITY_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "tournaments/hability/{habilityId}/edit")
	public String processUpdateForm(@Valid Hability hability, BindingResult result, @PathVariable("habilityId") int habilityId, ModelMap model) throws DataAccessException, SponsorAmountException, ReservedDateExeception{
		boolean edit = true;
		if(result.hasErrors()) {
			model.put("hability",hability);
			model.put("edit", edit);
			return VIEWS_HABILITY_CREATE_OR_UPDATE_FORM;
		} else {
			hability.setId(habilityId);
			model.put("edit", edit);
			try {
				this.habilityService.editHability(hability);
			} catch (ReservedDateExeception|SponsorAmountException ex) {
				if(ex.getClass() == ReservedDateExeception.class){
					result.rejectValue("date", "This date is already taken", "This date is already taken");
				}
				if(ex.getClass() == SponsorAmountException.class){
					result.rejectValue("status", "The total amount of sponsor contribution is under 7000.00EUR", "The total amount of sponsor contribution is under 7000.00EUR");
				}
				return VIEWS_HABILITY_CREATE_OR_UPDATE_FORM;
			}
			return "redirect:/tournaments";
		}
	}
}
