package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.RaceService;
import org.springframework.stereotype.Controller;

@Controller
public class RaceController {
	
	@Autowired
	private RaceService raceService;

}
