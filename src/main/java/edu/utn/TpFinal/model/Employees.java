package edu.utn.TpFinal.model;

import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@SuperBuilder
@Entity
@DiscriminatorValue(value="Employee")

public class Employees extends Persons {

}
