package edu.utn.TpFinal.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode

public class Countries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String shortName;
    @NotNull
    private Integer prefix;
    @OneToMany(mappedBy = "country")
    private List<Provinces> provinces;

}
