package edu.utn.TpFinal.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private Integer dni;
    @Column(name = "user_alias")
    @NotNull
    private String userName;
    @Column(name = "user_password")
    @NotNull
    private String password;
    @Column(name = "active")
    private Boolean active;


//    public enum Type{
//        CLIENT, EMPLOYEE
//    }
//    @Column(name = "p_type", insertable = false, updatable = false)
//    private Type dType;

    //ToDo consultar a germán.
}

