package edu.utn.TpFinal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@SuperBuilder
@Entity
@DiscriminatorValue(value="Client")
@Data
@ToString
@EqualsAndHashCode


public class Clients extends Persons {
    @JsonBackReference
    @OneToMany(mappedBy = "client")
    private List<Lines> line;

    /*public Clients(List<Lines> line) {
        this.line = line;
    }

    public Clients(Integer id, @NotNull String name, @NotNull String lastName, @NotNull Long dni, @NotNull String userName, @NotNull String password, Boolean active, List<Lines> line) {
        super(id, name, lastName, dni, userName, password, active);
        this.line = line;
    }*/
}

//ToDo verificar "active" sin valor x defecto.
