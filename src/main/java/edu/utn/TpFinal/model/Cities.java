package edu.utn.TpFinal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class Cities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private Integer prefix;
    @ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "cities")
    @JsonBackReference
    Provinces province;


}
