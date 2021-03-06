package org.springframework.samples.petclinic.web;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Race;
import org.springframework.samples.petclinic.service.RaceService;
import org.springframework.samples.petclinic.service.exceptions.JudgeNotFoundException;
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

import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Controller
public class RaceController {

	@Autowired
	private RaceService raceService;

	private static final String VIEWS_RACE_CREATE_OR_UPDATE_FORM = "tournaments/createOrUpdateRaceForm";

	@InitBinder("race")
	public void initRaceBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new RaceValidator());
	}
	
	@ModelAttribute("statusTypes")
	public Collection<String> getStatusTypes(){
		Collection<String> statusTypes = Arrays.asList("DRAFT","PENDING","FINISHED");
		return statusTypes;
	}
	
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
	public String processCreationForm(@Valid final Race race, final BindingResult result, final ModelMap model) {
		if (result.hasErrors()) {
			model.put("race", race);
			return RaceController.VIEWS_RACE_CREATE_OR_UPDATE_FORM;
		} else {
			try {
				race.setStatus("DRAFT");
				this.raceService.saveRace(race);
			} catch (ReservedDateExeception ex) {
				result.rejectValue("date", "This date is reserved", "This date is reserved");
				return RaceController.VIEWS_RACE_CREATE_OR_UPDATE_FORM;
			}
			
			return "redirect:/tournaments";
		}
	}
	
	@GetMapping(value= "/tournaments/race/{raceId}/edit")
	public String initUpdateForm(@PathVariable("raceId") int raceId, ModelMap model) {
		Race race = this.raceService.findRaceById(raceId);
		boolean edit = true;
		model.put("race", race);
		model.put("edit", edit);
		return VIEWS_RACE_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/tournaments/race/{raceId}/edit")
	public String processUpdateForm(@Valid Race race, BindingResult result,@PathVariable("raceId") int raceId, ModelMap model) throws DataAccessException, SponsorAmountException {		
		
		boolean edit = true;

		if(result.hasErrors()) {
			model.put("race", race);
			model.put("edit", edit);
			return VIEWS_RACE_CREATE_OR_UPDATE_FORM;
		} else {
			race.setId(raceId);
			model.put("edit", edit);

			try {
				this.raceService.editRace(race);
			}catch (ReservedDateExeception|SponsorAmountException|JudgeNotFoundException ex) {
				if(ex.getClass().equals(ReservedDateExeception.class)){
					result.rejectValue("date", "This date is already taken", "This date is already taken");
				}
				if(ex.getClass().equals(SponsorAmountException.class)){
					result.rejectValue("status", "The total amount of sponsor contribution is under 7000.00EUR", "The total amount of sponsor contribution is under 7000.00EUR");
				}
				if(ex.getClass().equals(JudgeNotFoundException.class)) {
					result.rejectValue("status", "Not judges found for this competition", "Not judges found for this competition");
				}
				
				return VIEWS_RACE_CREATE_OR_UPDATE_FORM;
			}
		}
		return "redirect:/tournaments";
	}

}
