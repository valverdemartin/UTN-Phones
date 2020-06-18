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
    @JoinColumn(name = "id_origin_city")
    @NotNull @ManyToOne(fetch = FetchType.EAGER)
    private Cities originCity;
    @JoinColumn(name = "id_dest_city")
    @NotNull @ManyToOne(fetch = FetchType.EAGER)
    private Cities destCity;
    @Column(name = "price_minute")
    @NotNull
    private double pricePerMinute;
    @Column(name = "rate_date")
    @NotNull
    private Date rateDate;
    @Column(name = "cost_price")
    @NotNull
    private Double costPrice;
}
