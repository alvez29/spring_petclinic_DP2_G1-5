
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Hability;
import org.springframework.samples.petclinic.model.Sponsor;
import org.springframework.stereotype.Repository;

@Repository
public interface HabilityRepository extends CrudRepository<Hability, Integer> {

	@Query(value = "SELECT date FROM Tournaments where date >= CURRENT_DATE", nativeQuery = true)
	List<String> getFutureDates();

	@Query("SELECT date FROM Tournament t where t.id =?1")
	LocalDate getDateByHanilityId(Integer id);

	@Query("SELECT s from Sponsor s where tournament.id = ?1")
//	@Query(value = "SELECT * FROM Sponsor where TOURNAMENT_ID = ?1", nativeQuery = true)
	Collection<Sponsor> getSponsors(Integer id);
}
