package edu.utn.TpFinal.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="Employee")

public class Employees extends Persons {

}
