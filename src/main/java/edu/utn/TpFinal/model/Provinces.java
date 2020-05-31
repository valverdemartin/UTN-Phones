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
@Table( name= "provinces")

public class Provinces {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "province_name")
    @NotNull
    private String name;
    ////////Province-City//////
    @OneToMany(mappedBy = "province")
    private List<Cities> cities;

}
