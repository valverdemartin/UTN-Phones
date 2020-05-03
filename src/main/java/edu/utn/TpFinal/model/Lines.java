package edu.utn.TpFinal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import javax.persistence.*;

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
    private Long phoneNumber;
    private Boolean type; //1 = movil // 0 = residencial
    private Integer cityPrefix;
    @ManyToOne
    //@JoinColumn(name = "line")
    @JsonBackReference
    private Clients client;

}
