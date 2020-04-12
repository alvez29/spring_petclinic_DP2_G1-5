package org.springframework.samples.petclinic.web;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.ResultScore;
import org.springframework.samples.petclinic.service.BeautyResultService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BeautyResultController {

	@Autowired
	private BeautyResultService  beautyResultService;

	@GetMapping("/tournaments/beauty/{tournamentId}/result")
	public String beautyResultList(@PathVariable("tournamentId") int tournamentId,ModelMap modelMap) {
		String vista = "tournaments/beautyResultList";
		List<ResultScore> beautyResults = beautyResultService.findByTournamentId(tournamentId);
		Collections.sort(beautyResults, Collections.reverseOrder((x,y)-> x.getTotalPoints().compareTo(y.getTotalPoints())));
		modelMap.addAttribute("beautyResults", beautyResults);
		return vista;
	}
	
	
}
