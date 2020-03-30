package org.springframework.samples.petclinic.web;

import java.util.List;
import java.io.UnsupportedEncodingException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.model.locationiqapi.Place;
import org.springframework.samples.petclinic.service.JudgeService;
import org.springframework.samples.petclinic.service.LocationIQAPIService;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Pet;
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
	private JudgeService judgeService;

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
	public String showTournament(@PathVariable("tournamentId") int tournamentId, ModelMap modelMap) throws UnsupportedEncodingException {
		
		Tournament tournament = this.tournamentService.findTournamentById(tournamentId);
		String vista = "tournaments/tournamentDetails";
		
	
		
		String site = this.tournamentService.getSite(tournamentId);
		
		if(site!=null) {
			Place[] places = LocationIQAPIService.getPlace(site);
			Place place = places[0];
			
			String lat = null;
			String lng = null;
			
			if(!place.equals(null)) {
				lat = place.getLat();
				lng = place.getLon();
			}
			
			modelMap.addAttribute("lat", lat);
			modelMap.addAttribute("lng", lng);
		}
		
		
		modelMap.addAttribute("tournament", tournament);

		return vista;
	}
	

	@GetMapping("/tournaments/{tournamentId}/addjudge/{judgeId}")
	public String linkJudgeToTournament(@PathVariable("tournamentId") int tournamentId, @PathVariable("judgeId") int judgeId, ModelMap modelMap) {
		Tournament tournament = this.tournamentService.findTournamentById(tournamentId);
		Judge judge = this.judgeService.findJudgeById(judgeId).get();
		tournament.addJudge(judge);
		this.tournamentService.saveTournament(tournament);
		return "redirect:/tournaments/"+ tournamentId;
	}

	@GetMapping("/tournaments/{tournamentId}/addpet/{petId}")
	public String linkPetToTournament(@PathVariable("tournamentId") int tournamentId, @PathVariable("petId") int petId, ModelMap modelMap) throws DataAccessException, DuplicatedPetNameException {
		Tournament tournament = this.tournamentService.findTournamentById(tournamentId);
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
		Tournament tournament = this.tournamentService.findTournamentById(tournamentId);
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
	
}
