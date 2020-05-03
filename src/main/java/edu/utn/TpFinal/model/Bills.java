/*package edu.utn.TpFinal.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class Bills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @OneToOne
    private Lines line;
    @NotNull
    @ManyToOne
    @JoinColumn(name="id")
    private Clients client;
    @NotNull
    private Integer callCounter;
    @NotNull
    private Double priceCost;
    @NotNull
    private Double totalPrice;
    @NotNull
    private Date date;
    @NotNull
    private Date dueDate;
}*/
