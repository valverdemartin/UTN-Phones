package edu.utn.TpFinal.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="Employee")

public class Employees extends Persons {

}
