
package org.springframework.samples.petclinic.web;

import java.util.Arrays;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Beauty;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.service.BeautyService;
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
public class BeautyController {

	@Autowired
	private BeautyService		beautyService;

	private static final String	VIEWS_BEAUTY_CREATE_OR_UPDATE_FORM	= "tournaments/createOrUpdateBeautyForm";


	@InitBinder("beauty")
	public void initRaceBinder(final WebDataBinder dataBinder) {
		dataBinder.setValidator(new BeautyValidator());
	}

	@Autowired
	public BeautyController(final BeautyService beautyService) {
		this.beautyService = beautyService;
	}

	@ModelAttribute("types")
	public Collection<PetType> populatePetTypes() {
		return this.beautyService.getAllTypes();
	}

	@ModelAttribute("statusTypes")
	public Collection<String> getStatusTypes() {
		Collection<String> statusTypes = Arrays.asList("DRAFT", "PENDING", "FINISHED");
		return statusTypes;
	}

	@GetMapping("/beauty/new")
	public String initCreationForm(final ModelMap model) {
		Beauty beauty = new Beauty();
		model.put("beauty", beauty);
		return BeautyController.VIEWS_BEAUTY_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/beauty/new")
	public String processCreationForm(@Valid final Beauty beauty, final BindingResult result, final ModelMap model) {
		if (result.hasErrors()) {
			model.put("beauty", beauty);
			return BeautyController.VIEWS_BEAUTY_CREATE_OR_UPDATE_FORM;
		} else {
			try {
				beauty.setStatus("DRAFT");
				this.beautyService.saveBeauty(beauty);
			} catch (ReservedDateExeception ex) {
				result.rejectValue("date", "This date is reserved", "This date is reserved");
				return BeautyController.VIEWS_BEAUTY_CREATE_OR_UPDATE_FORM;
			}

			return "redirect:/tournaments";
		}
	}

	@GetMapping(value = "tournaments/beauty/{beautyId}/edit")
	public String initUpdateForm(@PathVariable("beautyId") final int beautyId, final ModelMap model) {
		Beauty beauty = this.beautyService.findBeautyById(beautyId);
		boolean edit = true;
		model.put("beauty", beauty);
		model.put("edit", edit);
		return BeautyController.VIEWS_BEAUTY_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "tournaments/beauty/{beautyId}/edit")
	public String processUpdateForm(@Valid final Beauty beauty, final BindingResult result, @PathVariable("beautyId") final int beautyId, final ModelMap model) throws DataAccessException, SponsorAmountException, ReservedDateExeception {
		boolean edit = true;
		if (result.hasErrors()) {
			model.put("beauty", beauty);
			model.put("edit", edit);
			return BeautyController.VIEWS_BEAUTY_CREATE_OR_UPDATE_FORM;
		} else {
			beauty.setId(beautyId);
			model.put("edit", edit);
			try {
				this.beautyService.editBeauty(beauty);
			} catch (ReservedDateExeception | SponsorAmountException ex) {
				if (ex.getClass() == ReservedDateExeception.class) {
					result.rejectValue("date", "This date is already taken", "This date is already taken");
				}
				if (ex.getClass() == SponsorAmountException.class) {
					result.rejectValue("status", "The total amount of sponsor contribution is under 7000.00EUR", "The total amount of sponsor contribution is under 7000.00EUR");
				}
				return BeautyController.VIEWS_BEAUTY_CREATE_OR_UPDATE_FORM;
			}
			return "redirect:/tournaments";
		}
	}

}
