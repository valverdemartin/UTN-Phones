package edu.utn.TpFinal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

public class Provinces {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String name;
    ///////Province-Country//////
    @ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "provinces")
    @JsonBackReference
    @NotNull
    private Countries country;
    ////////Province-City//////
    @OneToMany(mappedBy = "province")
    private List<Cities> cities;
}
