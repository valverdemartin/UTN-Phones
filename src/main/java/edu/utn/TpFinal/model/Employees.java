package edu.utn.TpFinal.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@SuperBuilder
@Entity
@DiscriminatorValue(value="Employee")
@ToString
@EqualsAndHashCode

public class Employees extends Persons {
    public Employees() {
    }

    public Employees(PersonsBuilder<?, ?> b) {
        super(b);
    }

    public Employees(Integer id, @NotNull String name, @NotNull String lastName, @NotNull Integer dni, @NotNull String userName, @NotNull String password, Boolean active) {
        super(id, name, lastName, dni, userName, password, active);
    }


}
