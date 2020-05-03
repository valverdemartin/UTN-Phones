/*package edu.utn.TpFinal.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class Calls {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private Long originPhoneNumber;
    @NotNull
    private Long destinationPhoneNumber;
    @NotNull
    private String originCity;
    @NotNull
    private String destinationCity;
    @NotNull
    private Double minPrice;
    @NotNull
    private Long duration;
    @NotNull
    private Double totalPrice;
    @NotNull
    private Bills bill;
}*/
