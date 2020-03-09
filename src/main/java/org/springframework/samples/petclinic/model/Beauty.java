package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "beauty")
@DiscriminatorValue("Beauty")
public class Beauty extends Tournament {

    @Column(name = "place")
    public String place;


}
