package edu.utn.TpFinal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@SuperBuilder
@Builder
@Entity
@DiscriminatorValue(value="Client")
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

public class Clients extends Persons {
    @JsonBackReference
    @ToString.Exclude
    @OneToMany(mappedBy = "client")
    private List<Lines> line;

}

//ToDo verificar "active" sin valor x defecto.
