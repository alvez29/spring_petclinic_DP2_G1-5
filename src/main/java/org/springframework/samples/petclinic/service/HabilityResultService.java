
package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.samples.petclinic.model.ResultTime;
import org.springframework.samples.petclinic.repository.springdatajpa.ResultTimeRepository;
import org.springframework.stereotype.Service;

@Service
public class HabilityResultService {

	@Autowired
	private ResultTimeRepository resultTimeRepository;


	@Query("select r from ResultTime r where r.tournament.id = ?1")
	public List<ResultTime> findByTournamentId(final int tournamentId) {

		return this.resultTimeRepository.findByTournamentId(tournamentId);
	}

}
