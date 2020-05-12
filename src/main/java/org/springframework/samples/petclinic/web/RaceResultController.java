package org.springframework.samples.petclinic.web;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.ResultTime;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.RaceResultService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedResultForPetInTournament;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RaceResultController {
	
	private static final String VIEW_RACE_ADD_RESULT = "tournaments/createOrUpdateRaceResultForm";

	@Autowired
	private RaceResultService raceResultService;
	
	@Autowired
	private PetService petService;
	
	@Autowired
	private TournamentService tournamentService;
	
	
	@GetMapping("/tournaments/race/{tournamentId}/result")
	public String raceResultList(ModelMap modelMap, @PathVariable("tournamentId") int tournamnetId) {
		String vista = "tournaments/raceResultList";
		List<ResultTime> results = raceResultService.findByTournamnetId(tournamnetId);
		Collections.sort(results, (x,y)-> x.getTime().compareTo(y.getTime()));
		modelMap.addAttribute("results", results);
		return vista;
	}
	
	@GetMapping("/tournament/race/{tournamentId}/pet/{petId}/add_result")
	public String initCreationForm(ModelMap model, @PathVariable("tournamentId") int tournamentId, @PathVariable("petId") int petId) {
		if(!this.raceResultService.isInTournament(tournamentId, petId)) {
			return "redirect:/oups";
		}else {
			ResultTime result = new ResultTime();
			model.put("resultTime",result);
			return VIEW_RACE_ADD_RESULT;
		}
	}
	
	@PostMapping(value = "/tournament/race/{tournamentId}/pet/{petId}/add_result")
	public String processCreationForm(@Valid  ResultTime resultTime, BindingResult result, ModelMap model, @PathVariable("tournamentId") int tournamnetId, @PathVariable("petId") int petId) {
		if (result.hasErrors()) {
			model.put("resultTime",resultTime);
			return VIEW_RACE_ADD_RESULT;
		} else {
			try {
				resultTime.setPet(petService.findPetById(petId));
				resultTime.setTournament(tournamentService.findTournamentById(tournamnetId));
				raceResultService.saveResult(resultTime);
				return "redirect:/tournaments/"+tournamnetId;
				
			}catch(DuplicatedResultForPetInTournament e) {
				return "redirect:/oups";

			}
		}
	}
	
	@GetMapping(value = "/tournaments/race/{tournamentId}/result/{resultId}/delete")
	public String deleteResult(@PathVariable("resultId") int resultId, @PathVariable("tournamentId") int tournamentId) {
		this.raceResultService.deleteRaceResult(resultId);
		return "redirect:/tournaments/race/"+tournamentId+"/result";
	}
}
