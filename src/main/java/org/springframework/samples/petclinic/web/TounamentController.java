package org.springframework.samples.petclinic.web;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TounamentController {
	
	@Autowired
	private TournamentService tournamentService;
	
	@Autowired
	private PetService petService;
	
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
	
	
	@GetMapping("/tournaments/{tournamentId}/addpet/{petId}")
	public String linkPetToTournament(@PathVariable("tournamentId") int tournamentId, @PathVariable("petId") int petId, ModelMap modelMap) throws DataAccessException, DuplicatedPetNameException {
		Tournament tournament = this.tournamentService.findTournamentById(tournamentId).get();
		Pet pet = this.petService.findPetById(petId);
		List<Pet> pets = tournament.getPets();
		if (!pets.contains(pet)) {
			if (tournament.getBreedRestriction() != null) {
				if (pet.getType().toString().equals(tournament.getBreedRestriction().toString())) {
					tournament.addPet(pet);
					this.tournamentService.saveTournament(tournament);
				}
			}else {
				if (pet.getType() != null) {
					tournament.addPet(pet);
					this.tournamentService.saveTournament(tournament);
				}
				
			}
		}
		return "redirect:/tournaments/" + tournamentId;
	}
	
	
	@GetMapping("/pet/tournament/{tournamentId}")
	public String petListForTournament(@PathVariable("tournamentId") int tournamentId, ModelMap model) {
		List<Pet> pets = (List<Pet>) petService.findAll();
		Tournament tournament = this.tournamentService.findTournamentById(tournamentId).get();
		if (tournament.getBreedRestriction() != null) {
			List<Pet> petsBreed = pets.stream().filter(x->!x.getType()
					.equals(tournament.getBreedRestriction())).collect(Collectors.toList());
			pets.removeAll(petsBreed);
			pets.removeAll(tournament.getPets());
		}else {
			pets.removeAll(tournament.getPets());
		}
		model.addAttribute("pets", pets);
		model.addAttribute("tournamentId", tournamentId);
		return "pets/petList";
	}
	
	/*
	public String linkJudgeToTournament(@PathVariable("tournamentId") int tournamentId, @PathVariable("judgeId") int judgeId, ModelMap modelMap) {
		Tournament tournament = this.tournamentService.findTournamentById(tournamentId).get();
		Judge judge = this.judgeService.findJudgeById(judgeId).get();
		tournament.addJudge(judge);
		judge.addTournament(tournament);
		this.judgeService.saveJudge(judge);
		this.tournamentService.saveTournament(tournament);
		return "redirect:/tournaments/"+ tournamentId;
	}
	*/
	
	
	
}
