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
@Table( name= "cities")
public class Cities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "city_name")
    @NotNull
    private String name;
    @Column(name = "city_short_name")
    @NotNull
    private  String shortName;
    @Column(name = "city_prefix")
    @NotNull
    private Integer prefix;
    @JoinColumn(name = "id_province")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Provinces province;
    @OneToMany(mappedBy = "city")
    private List<Lines> lines;
}
