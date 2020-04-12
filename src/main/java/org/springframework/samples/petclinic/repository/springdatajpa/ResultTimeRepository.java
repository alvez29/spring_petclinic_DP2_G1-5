
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.ResultTime;

public interface ResultTimeRepository extends CrudRepository<ResultTime, Integer> {

	List<ResultTime> findByTournamentId(int tournamentId);

}
