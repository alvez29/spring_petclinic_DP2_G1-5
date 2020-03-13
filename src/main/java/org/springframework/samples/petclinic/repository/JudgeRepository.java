package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Judge;

/**
 * Repository class for <code>Judge</code> domain object
 * 
 * 
 * @author Acunnia
 *
 */
public interface JudgeRepository {
	
	Collection<Judge> findAll() throws DataAccessException;
}
