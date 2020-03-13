package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.repository.JudgeRepository;

public interface SpringDataJudgeRepository extends JudgeRepository, Repository<Judge, Integer>{

}
