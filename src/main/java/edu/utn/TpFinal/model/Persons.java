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
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "p_type")
@Table( name= "users")

public abstract class Persons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_name")
    @NotNull
    private String name;
    @Column(name = "user_last_name")
    @NotNull
    private String lastName;
    @Column(name = "user_dni")
    @NotNull
    private Long dni;
    @Column(name = "user_alias")
    @NotNull
    private String userName;
    @Column(name = "user_password")
    @NotNull
    private String password;
    @Column(name = "active")
    private Boolean active;
}

