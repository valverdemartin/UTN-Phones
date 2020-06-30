package edu.utn.TpFinal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
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
@AllArgsConstructor
@NoArgsConstructor

public class Clients extends Persons {
    @JsonBackReference
    @ToString.Exclude
    @OneToMany(mappedBy = "client")
    private List<Lines> line;

}

