package org.springframework.samples.petclinic.web;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Race;
import org.springframework.samples.petclinic.service.RaceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RaceController {

	@Autowired
	private RaceService raceService;

	private static final String VIEWS_RACE_CREATE_OR_UPDATE_FORM = "tournaments/createOrUpdateRaceForm";

	@Autowired
	public RaceController(RaceService raceService) {
		this.raceService = raceService;
	}
	
	@ModelAttribute("types")
	public Collection<PetType> populatePetTypes(){
		return this.raceService.getAllTypes();
		
	}
	
	@GetMapping("/race/new")
	public String initCreationForm( ModelMap model) {
		Race race = new Race();
		model.put("race", race);
		return VIEWS_RACE_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/race/new")
	public String processCreationForm(@Valid Race race, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.put("race", race);
			return VIEWS_RACE_CREATE_OR_UPDATE_FORM;
		} else {
			race.setStatus("PENDING");
			this.raceService.saveRace(race);
			return "redirect:/tournaments";
		}
	}

}
