package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.ResultTime;

public interface ResultTimeRepository extends CrudRepository<ResultTime, Integer> {

	@Query("select r from ResultTime r where r.tournament.id = ?1")
	List<ResultTime> findByTournamentId(int tournamnetId);

	@Query("select rt.pet from ResultTime rt where rt.tournament.id = ?1")
	List<Pet> findPetsWithResultByTournamentId(int tournamentId);

	@Query("select count(rt) from ResultTime rt where rt.pet.id = ?1 and rt.tournament.id = ?2")
	int hasResult(Integer petId, Integer tournamentId);
}

