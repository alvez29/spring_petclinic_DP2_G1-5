package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Sponsor;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.service.SponsorService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedSponsorNameException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tournaments/{tournamentId}")
public class SponsorController {


	private static final String VIEWS_SPONSORS_CREATE_OR_UPDATE_FORM = "sponsors/createOrUpdateSponsorForm";
	
	private final SponsorService sponsorService;
	private final TournamentService tournamentService;
	
	@Autowired
	public SponsorController(SponsorService sponsorService, TournamentService tournamentService) {
		this.sponsorService = sponsorService;
		this.tournamentService = tournamentService;
	}
	
	@ModelAttribute("tournament")
	public Tournament findTournament(@PathVariable("tournamentId") int tournamentId) {
		return this.tournamentService.findTournamentById(tournamentId);
	}
	
	@GetMapping(value = "/sponsors/add")
	public String initCreationForm(Tournament tournament, ModelMap model) {
		Sponsor sponsor = new Sponsor();
		tournament.addSponsor(sponsor);
		model.put("sponsor", sponsor);
		return VIEWS_SPONSORS_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/sponsors/add")
	public String processCreationForm(Tournament tournament, @Valid Sponsor sponsor, BindingResult result, ModelMap model) {		
		if (result.hasErrors()) {
			model.put("sponsor", sponsor);
			return VIEWS_SPONSORS_CREATE_OR_UPDATE_FORM;
		}
		else {
			try{
	       	tournament.addSponsor(sponsor);
	        	this.sponsorService.saveSponsor(sponsor);
	        }catch(DuplicatedSponsorNameException ex){
	        	result.rejectValue("name", "duplicate", "already exists");
	        	return VIEWS_SPONSORS_CREATE_OR_UPDATE_FORM;
	        }
	        return "redirect:/tournaments/{tournamentId}";
		}
	}

	
	
	
	
}
