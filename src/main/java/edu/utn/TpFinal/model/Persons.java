package edu.utn.TpFinal.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public abstract class Persons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String lastName;
    @NotNull
    private Long dni;
    @NotNull
    private String userName;
    @NotNull
    private String password;
    /*@NotNull
    @ManyToOne
    @JoinColumn(name="id")
    private Cities city;
    @NotNull
    @ManyToOne
    @JoinColumn(name="id")
    private Provincies province;*/
}
