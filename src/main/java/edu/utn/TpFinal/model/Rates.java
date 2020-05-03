package edu.utn.TpFinal.model;
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
public class Rates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull @ManyToOne(fetch = FetchType.EAGER)
    private Cities originCity;
    @NotNull @ManyToOne(fetch = FetchType.EAGER)
    private Cities destCity;
    @NotNull
    private double pricePerMinute;
    @NotNull
    private Date rateDate;
}
