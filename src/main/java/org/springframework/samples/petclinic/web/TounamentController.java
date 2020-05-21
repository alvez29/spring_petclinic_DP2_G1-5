package org.springframework.samples.petclinic.web;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.Period;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.model.Visit;
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
		
		Integer[] petHasResult = this.tournamentService.petHasResult(tournamentId);
		
		if(site!=null) {
			
			String lat = "";
			String lng = "";
			
			
			Place[] places = tournamentService.getPlace(site);
			Place[] empty = {};
			if(!Arrays.asList(places).isEmpty()) {
			
				Place place = places[0];

				if(!place.equals(null)) {
					lat = place.getLat();
					lng = place.getLon();
				}
			
			}else{
				lat = "";
				lng = "";
			}
		
			modelMap.addAttribute("lat", lat);
			modelMap.addAttribute("lng", lng);
		}
		
		
		modelMap.addAttribute("tournament", tournament);
		modelMap.addAttribute("petHasResult", petHasResult);

		return vista;
	}
	

	@GetMapping("/tournaments/{tournamentId}/addjudge/{judgeId}")
	public String linkJudgeToTournament(@PathVariable("tournamentId") int tournamentId, @PathVariable("judgeId") int judgeId, ModelMap modelMap) {
		Tournament tournament = this.tournamentService.findTournamentById(tournamentId);
		Judge judge = this.judgeService.findJudgeById(judgeId);
		tournament.addJudge(judge);
		this.tournamentService.saveTournament(tournament);
		return "redirect:/tournaments/"+ tournamentId;
	}

	@GetMapping("/tournaments/{tournamentId}/addpet/{petId}")
	public String linkPetToTournament(@PathVariable("tournamentId") int tournamentId, @PathVariable("petId") int petId, ModelMap modelMap) throws DataAccessException, DuplicatedPetNameException {
		Tournament tournament = this.tournamentService.findTournamentById(tournamentId);
		Pet pet = this.petService.findPetById(petId);
		List<Pet> pets = tournament.getPets();
		String res = "redirect:/tournaments/" + tournamentId;
		if (!pets.contains(pet)) {
			if (tournament.getBreedRestriction() != null) {
				if (pet.getType().toString().equals(tournament.getBreedRestriction().toString()) &&
						chechVisits(pet.getVisits(), tournament.getDate())) {
					tournament.addPet(pet);
					this.tournamentService.saveTournament(tournament);
				}else {
					res = "redirect:/oups";
				}
			}else {
				if (pet.getType() != null) {
					if (chechVisits(pet.getVisits(), tournament.getDate())) {
						tournament.addPet(pet);
						this.tournamentService.saveTournament(tournament);
					}else {
						res = "redirect:/oups";
					}
					
				}
				
			}
		}else {
			res = "redirect:/oups";
		}
		return res;
	}
	
	
	@GetMapping("/pet/tournament/{tournamentId}")
	public String petListForTournament(@PathVariable("tournamentId") int tournamentId, ModelMap model) {
		List<Pet> pets = (List<Pet>) petService.findAll();
		Tournament tournament = this.tournamentService.findTournamentById(tournamentId);
		List<Pet> petsOnTournament = tournament.getPets();
		if (tournament.getBreedRestriction() != null) {
			List<Pet> petsBreed = pets.stream().filter(x->x.getType()
					.equals(tournament.getBreedRestriction())  && chechVisits(this.petService.findVisitsByPetId(x.getId()),tournament.getDate())).collect(Collectors.toList());
			pets = petsBreed;
			pets.removeAll(petsOnTournament);
		}else {
			List<Pet> petsAllowed = pets.stream().filter(x->chechVisits(this.petService.findVisitsByPetId(x.getId()), tournament.getDate())).collect(Collectors.toList());
			pets = petsAllowed;
			pets.removeAll(petsOnTournament);
		}
		model.addAttribute("pets", pets);
		model.addAttribute("tournamentId", tournamentId);
		return "pets/petList";
	}	
	
	
	public static Boolean chechVisits(Collection<Visit> visits, LocalDate tournamentDate) {
		Boolean res = false;
		for (Visit v : visits) {
			if (v.getCompetitionCheck().equals("PASSED")) {
				if (v.getDate().isAfter(tournamentDate.minus(Period.ofMonths(1)))) {
					res = true;
					break;
				}
			}
		}
		return res;
	}
	
	
}
