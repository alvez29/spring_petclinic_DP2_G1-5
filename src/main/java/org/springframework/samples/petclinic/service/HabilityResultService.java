
package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.ResultTime;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.repository.springdatajpa.ResultTimeRepository;
import org.springframework.samples.petclinic.repository.springdatajpa.TournamentRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedResultForPetInTournament;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HabilityResultService {

	@Autowired
	private ResultTimeRepository	resultTimeRepository;

	@Autowired
	private TournamentRepository	tournamentRepo;


	@Query("select r from ResultTime r where r.tournament.id = ?1")
	public List<ResultTime> findByTournamentId(final int tournamentId) {

		return this.resultTimeRepository.findByTournamentId(tournamentId);
	}

	public void deleteResult(final int resultTime) {
		ResultTime result = this.resultTimeRepository.findById(resultTime).get();
		this.resultTimeRepository.delete(result);
	}

	@Transactional(rollbackFor = DuplicatedResultForPetInTournament.class)
	public void saveResult(@Valid final ResultTime resultTime) throws DuplicatedResultForPetInTournament {
		Integer petId = resultTime.getPet().getId();
		Integer tournamentId = resultTime.getTournament().getId();

		if (this.resultTimeRepository.hasResult(petId, tournamentId) != 0) {
			throw new DuplicatedResultForPetInTournament();
		} else {
			this.resultTimeRepository.save(resultTime);
		}
	}

	public boolean isInTournament(final int tournamentId, final int petId) {
		Tournament tourn = this.tournamentRepo.findById(tournamentId).get();
		List<Pet> participants = tourn.getPets();
		List<Integer> participantsIds = participants.stream().map(x -> x.getId()).collect(Collectors.toList());
		return participantsIds.contains(petId);
	}

}
