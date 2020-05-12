
package org.springframework.samples.petclinic.web;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.ResultTime;
import org.springframework.samples.petclinic.service.HabilityResultService;
import org.springframework.samples.petclinic.service.HabilityService;
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
public class HabilityResultController {

	@Autowired
	private HabilityResultService	habilityResultService;

	@Autowired
	private PetService				petService;

	@Autowired
	private TournamentService		tournamentService;

	@Autowired
	private HabilityService			habilityService;

	private static final String		VIEWS_RESULT_CREATE_OR_UPDATE_FORM	= "tournaments/createOrUpdateHabilityResultForm";


	@GetMapping("/tournaments/hability/{tournamentId}/result")
	public String habilityResultList(final ModelMap modelMap, @PathVariable("tournamentId") final int tournamentId) {
		String vista = "tournaments/habilityResultList";
		List<ResultTime> results = this.habilityResultService.findByTournamentId(tournamentId);
		Collections.sort(results, (x, y) -> x.getTotalResult().compareTo(y.getTotalResult()));
		modelMap.addAttribute("results", results);
		return vista;
	}

	@GetMapping("/tournament/hability/{tournamentId}/pet/{petId}/add_result")
	public String initCreationForm(final ModelMap model, @PathVariable("tournamentId") final int tournamentId, @PathVariable("petId") final int petId) {

		if (!this.habilityResultService.isInTournament(tournamentId, petId)) {
			return "redirect:/oups";
		} else {
			ResultTime result = new ResultTime();
			model.put("resultTime", result);
			return HabilityResultController.VIEWS_RESULT_CREATE_OR_UPDATE_FORM;
		}
	}

	@PostMapping(value = "/tournament/hability/{tournamentId}/pet/{petId}/add_result")
	public String processCreationForm(@Valid final ResultTime resultTime, final BindingResult result, final ModelMap model, @PathVariable("tournamentId") final int tournamentId, @PathVariable("petId") final int petId) {
		if (result.hasErrors()) {
			model.put("resultTime", resultTime);
			return HabilityResultController.VIEWS_RESULT_CREATE_OR_UPDATE_FORM;
		} else {
			try {
				resultTime.setPet(this.petService.findPetById(petId));
				resultTime.setTournament(this.tournamentService.findTournamentById(tournamentId));
				this.habilityResultService.saveResult(resultTime);
				return "redirect:/tournaments/" + tournamentId;
			} catch (DuplicatedResultForPetInTournament e) {
				return "redirect:/oups";
			}

		}

	}

	@GetMapping(value = "tournaments/hability/{tournamentId}/result/{resultId}/delete")
	public String delete(@PathVariable("tournamentId") final int tournamentId, @PathVariable("resultId") final int resultId) {

		this.habilityResultService.deleteResult(resultId);

		return "redirect:/tournaments/hability/" + tournamentId + "/result";
	}

}
