package org.springframework.samples.petclinic.web;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.ResultTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RaceResultController {
	
	@Autowired
	private RaceResultService raceResultService;
	
	@GetMapping("/tournaments/race/{tournamentId}/result")
	public String raceResultList(ModelMap modelMap, @PathVariable("tournamentId") int tournamnetId) {
		String vista = "tournaments/raceResultList";
		List<ResultTime> results = raceResultService.findByTournamnetId(tournamnetId);
		Collections.sort(results, (x,y)-> x.getTime().compareTo(y.getTime()));
		modelMap.addAttribute("results", results);
		return vista;
	}
}

