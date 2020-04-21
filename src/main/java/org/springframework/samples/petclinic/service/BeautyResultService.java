package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.ResultScore;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.repository.springdatajpa.ResultScoreRepository;
import org.springframework.samples.petclinic.repository.springdatajpa.TournamentRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedResultForPetInTournament;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BeautyResultService {

	@Autowired
	private ResultScoreRepository resultScoreRepo;
	
	@Autowired
	private TournamentRepository tournamentRepo;
	
	@Autowired
	public BeautyResultService(final ResultScoreRepository resultScoreRepo, final TournamentRepository tournamentRepo) {
		this.resultScoreRepo = resultScoreRepo;
		this.tournamentRepo = tournamentRepo;
	}
	
	
	public List<ResultScore> findByTournamentId(int id) {
		return resultScoreRepo.findByTournamentId(id);
	}


	@Transactional(rollbackFor = DuplicatedResultForPetInTournament.class)
	public void save(@Valid ResultScore resultScore) throws DuplicatedResultForPetInTournament {
		
		Integer petId = resultScore.getPet().getId();
		Integer tournamentId = resultScore.getTournament().getId();
		
		if(this.resultScoreRepo.hasResult(petId,tournamentId) != 0) {
			throw new DuplicatedResultForPetInTournament();
		}else {
			this.resultScoreRepo.save(resultScore);
		}
		
	}
	public boolean isInTournament(int tournamentId, int petId) {
 
		Tournament tourn = this.tournamentRepo.findById(tournamentId).get();
		List<Pet> participants = tourn.getPets();
		List<Integer> participantsIds = participants.stream()
													.map(x->x.getId())
													.collect(Collectors.toList());	
		return participantsIds.contains(petId);
	}
	
}
