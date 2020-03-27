package org.springframework.samples.petclinic.web;


import java.io.UnsupportedEncodingException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.model.googlemapsapi.Place;
import org.springframework.samples.petclinic.service.GoogleMapsAPIService;
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
	public String showTournament(@PathVariable("tournamentId") int tournamentId, ModelMap modelMap) throws UnsupportedEncodingException {
		
		Tournament tournament = this.tournamentService.findTournamentById(tournamentId);
		String vista = "tournaments/tournamentDetails";
		
	
		
		String site = this.tournamentService.getSite(tournamentId);
		
		if(site!=null) {
			Place place = GoogleMapsAPIService.getPlace(site);
			
			Double lat = null;
			Double lng = null;
			
			if(!place.getResults().isEmpty()) {
				lat = place.getResults().get(0).getGeometry().getLocation().getLat();
				lng = place.getResults().get(0).getGeometry().getLocation().getLng();
			}
			
			modelMap.addAttribute("lat", lat);
			modelMap.addAttribute("lng", lng);
		}
		
		
		modelMap.addAttribute("tournament", tournament);

		return vista;
	}
	
	
	
}
