package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Judge;

public interface JudgeRepository extends CrudRepository<Judge, Integer>{

}
