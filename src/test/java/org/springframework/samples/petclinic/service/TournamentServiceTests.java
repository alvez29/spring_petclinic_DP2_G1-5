package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Sponsor;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class TournamentServiceTests {

	@Autowired
	private TournamentService tournamnetService;
	
	private Tournament tournament;
	
	@BeforeEach
	void setUp() {
		tournament.setCapacity(900);
		tournament.setDate(LocalDate.of(2040, 12, 22));
		tournament.setName("New tournament");
		tournament.setRewardMoney(100000.0);
		tournament.setStatus("DRAFT");
		
				
	}

	
		
}
