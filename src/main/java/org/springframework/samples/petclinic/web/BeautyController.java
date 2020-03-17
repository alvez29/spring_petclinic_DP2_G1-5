package org.springframework.samples.petclinic.web;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Beauty;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.service.BeautyService;
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
public class BeautyController {

	@Autowired
	private BeautyService beautyService;
	
	private static final String VIEWS_BEAUTY_CREATE_OR_UPDATE_FORM = "tournaments/createOrUpdateBeautyForm";

	@InitBinder("beauty")
	public void initRaceBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new BeautyValidator());
	}
	
	@Autowired
	public BeautyController(BeautyService beautyService) {
		this.beautyService = beautyService;
	}
	
	@ModelAttribute("types")
	public Collection<PetType> populatePetTypes(){
		return this.beautyService.getAllTypes();
	}
	
	@GetMapping("/beauty/new")
	public String initCreationForm(ModelMap model){
		Beauty beauty = new Beauty();
		model.put("beauty", beauty);
		return VIEWS_BEAUTY_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/beauty/new")
	public String processCreationForm(@Valid Beauty beauty, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.put("beauty", beauty);
			return VIEWS_BEAUTY_CREATE_OR_UPDATE_FORM;
		}else {
			try {
				beauty.setStatus("PENDING");
				this.beautyService.saveBeauty(beauty);
			} catch (ReservedDateExeception ex) {
				result.rejectValue("date", "This date is reserved","This date is reserved");
				return VIEWS_BEAUTY_CREATE_OR_UPDATE_FORM;
			}
			
			return "redirect:/tournaments";
		}
	}
}
