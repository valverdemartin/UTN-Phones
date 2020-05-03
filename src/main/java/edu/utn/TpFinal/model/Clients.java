package edu.utn.TpFinal.model;

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
    @OneToMany(mappedBy = "client")
    private List<Lines> line;
    /*@OneToMany
    @JoinColumn(name = "Bills")
    private Bills bill;*/
}
