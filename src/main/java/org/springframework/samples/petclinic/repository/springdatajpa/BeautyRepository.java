package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Beauty;
import org.springframework.stereotype.Repository;

@Repository
public interface BeautyRepository extends CrudRepository<Beauty, Integer>{

}
