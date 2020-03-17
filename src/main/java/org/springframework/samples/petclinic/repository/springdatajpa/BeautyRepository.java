package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Beauty;
import org.springframework.stereotype.Repository;

@Repository
public interface BeautyRepository extends CrudRepository<Beauty, Integer>{

	@Query(value = "SELECT date FROM Tournaments where date >= CURRENT_DATE", nativeQuery = true)
	List<String> getFutureDates();

}
