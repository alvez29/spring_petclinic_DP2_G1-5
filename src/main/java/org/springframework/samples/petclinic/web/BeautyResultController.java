package org.springframework.samples.petclinic.web;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.ResultScore;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.service.BeautyResultService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedResultForPetInTournament;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BeautyResultController {

	private static final String VIEW_BEAUTY_ADD_RESULT = "/tournaments/createOrUpdateBeautyResultForm";

	@Autowired
	private BeautyResultService  beautyResultService;
	
	@Autowired
	private PetService petService;
	
	@Autowired
	private TournamentService tournamentService;

	@GetMapping("/tournaments/beauty/{tournamentId}/result")
	public String beautyResultList(@PathVariable("tournamentId") int tournamentId,ModelMap modelMap) {
		String vista = "tournaments/beautyResultList";
		List<ResultScore> beautyResults = beautyResultService.findByTournamentId(tournamentId);
		Collections.sort(beautyResults, Collections.reverseOrder((x,y)-> x.getTotalPoints().compareTo(y.getTotalPoints())));
		modelMap.addAttribute("beautyResults", beautyResults);
		return vista;
	}
	
	@GetMapping("/tournament/beauty/{tournamentId}/pet/{petId}/add_result")
	public String initCreationForm(@PathVariable("tournamentId") int tournamentId, @PathVariable("petId") int petId, ModelMap model) {
		if(!this.beautyResultService.isInTournament(tournamentId, petId)) {
			return "redirect:/oups";
		}else {
			ResultScore resultScore = new ResultScore();	
			model.put("resultScore", resultScore);
			return VIEW_BEAUTY_ADD_RESULT;
		}
		
	}
	
	
	@PostMapping(value="/tournament/beauty/{tournamentId}/pet/{petId}/add_result")
	public String processCreateForm(@Valid ResultScore resultScore, BindingResult result, @PathVariable("tournamentId") int tournamentId, @PathVariable("petId") int petId, ModelMap model){
		if (result.hasErrors()) {
			model.put("resultScore", resultScore);
			return VIEW_BEAUTY_ADD_RESULT;
		} else {
			try{
				Pet pet = this.petService.findPetById(petId);
				Tournament tournament = this.tournamentService.findTournamentById(tournamentId);
				
				resultScore.setPet(pet);
				resultScore.setTournament(tournament);
				
				this.beautyResultService.save(resultScore);
				return "redirect:/tournaments/"+tournamentId;
			}catch(DuplicatedResultForPetInTournament e) {
				return "redirect:/oups";
			}
			
		}
	}
	
	@GetMapping(value="tournaments/beauty/{tournamentId}/result/{beautyResultId}/delete")
	public String delete(@PathVariable("tournamentId") int tournamentId, @PathVariable("beautyResultId") int beautyResultId) {
		this.beautyResultService.delete(beautyResultId);
		return "redirect:/tournaments/beauty/{tournamentId}/result";
		
	}
	
}
