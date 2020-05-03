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

public class Lines {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private Long phoneNumber;
    @NotNull
    private Boolean type; //1 = movil // 0 = residencial
    @NotNull
    private Integer cityPrefix;
    @ManyToOne
    //@JoinColumn(name = "line")
    @JsonBackReference
    private Clients client;

}
