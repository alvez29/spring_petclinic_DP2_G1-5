package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Sponsor;

public interface SponsorRepository extends CrudRepository<Sponsor, Integer>{

	@Query("SELECT sponsor FROM Sponsor sponsor ORDER BY sponsor.name")
	List<Sponsor> findSponsors() throws DataAccessException;



}
