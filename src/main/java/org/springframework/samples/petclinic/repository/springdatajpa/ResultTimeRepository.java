package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.ResultTime;

public interface ResultTimeRepository extends CrudRepository<ResultTime, Integer> {

	@Query("select r from ResultTime r where r.tournament.id = ?1")
	List<ResultTime> findByTournamentId(int tournamnetId);
}
