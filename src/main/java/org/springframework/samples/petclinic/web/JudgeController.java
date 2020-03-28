package org.springframework.samples.petclinic.web;

import java.util.HashMap;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.service.JudgeService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JudgeController {

	@Autowired
	private JudgeService judgeService;
	
	@Autowired
	private TournamentService tournamentService;
	
	@Autowired
	public JudgeController(final JudgeService service) {
		this.judgeService = service;
	}
	
	@GetMapping("/judge")
	public String judgeList(ModelMap model) {
		String view = "judge/judgeList";
		Iterable<Judge> judges = judgeService.findAll();
		model.addAttribute("judges", judges);
		return view;
	}
	
	@GetMapping("/judge/tournament/{tournamentId}")
	public String judgeListforTournament(@PathVariable("tournamentId") int tournamentId, ModelMap model) {
		String view = "judge/judgeList";
		Iterable<Judge> judges = judgeService.findAll();
//		Tournament tournament = tournamentService.findTournamentById(tournamentId).get();
		model.addAttribute("judges", judges);
		model.addAttribute("tournamentId", tournamentId);
		return view;
	}
	
	@GetMapping("/judge/{judgeId}")
	public String showJudge(@PathVariable("judgeId") int judgeId, ModelMap model) {
		Judge judge = this.judgeService.findJudgeById(judgeId);
		String vista = "judge/judgeDetails";
		model.addAttribute("judge", judge);
		return vista;
	}
		
	@GetMapping("/judge/new")
	public String initCreationForm(final ModelMap model) {
		Judge judge = new Judge();
		model.put("judge", judge);
		return "judge/createOrUpdateJudgeForm";
	}
	
	@PostMapping(value = "/judge/new")
	public String processCreationForm(@Valid final Judge judge, final BindingResult result, final ModelMap model) {
		if (result.hasErrors()) {
			model.put("judge", judge);
			return "judge/createOrUpdateJudgeForm";
		} else {
			this.judgeService.saveJudge(judge);
			return "redirect:/judge";
		}
	}
}
