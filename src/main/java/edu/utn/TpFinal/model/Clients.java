package edu.utn.TpFinal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import javax.persistence.*;
import java.util.List;


@Entity
@DiscriminatorValue(value="Client")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode

public class Clients extends Persons {
    @JsonBackReference
    @OneToMany(mappedBy = "client")
    private List<Lines> line;
}

//ToDo verificar "active" sin valor x defecto.
