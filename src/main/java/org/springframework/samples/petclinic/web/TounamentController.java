package org.springframework.samples.petclinic.web;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TounamentController {
	
	@Autowired
	private TournamentService tournamentService;
	
	@GetMapping("/tournaments")
	public String tournamentList(ModelMap modelMap) {
		String vista = "tournaments/tournamentList";
		Iterable<Tournament> tournaments = tournamentService.findAll();
		modelMap.addAttribute("tournaments", tournaments);
		return vista;
	}

	@GetMapping("/tournaments/{tournamentId}")
	public String showTournament(@PathVariable("tournamentId") int tournamentId, ModelMap modelMap) {
		Optional<Tournament> tournament = this.tournamentService.findTournamentById(tournamentId);
		String vista = "tournaments/tournamentDetails";
		modelMap.addAttribute("tournament", tournament.get());
		return vista;
	}
}
