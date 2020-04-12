
package org.springframework.samples.petclinic.web;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.ResultTime;
import org.springframework.samples.petclinic.service.HabilityResultService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HabilityResultController {

	@Autowired
	private HabilityResultService habilityResultService;


	@GetMapping("/tournaments/hability/{tournamentId}/result")
	public String habilityResultList(final ModelMap modelMap, @PathVariable("tournamentId") final int tournamentId) {
		String vista = "tournaments/habilityResultList";
		List<ResultTime> results = this.habilityResultService.findByTournamentId(tournamentId);
		Collections.sort(results, (x, y) -> x.getTotalResult().compareTo(y.getTotalResult()));
		modelMap.addAttribute("results", results);
		return vista;
	}

}
