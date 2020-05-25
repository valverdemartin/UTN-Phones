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
@Table(name = "calls")

public class Calls {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JoinColumn(name = "id_rate")
    @NotNull @ManyToOne(fetch = FetchType.EAGER)
    private Rates rate;
    @JoinColumn(name = "id_origin_line")
    @NotNull @ManyToOne(fetch = FetchType.EAGER)
    private Lines originLine;
    @JoinColumn(name = "id_dest_line")
    @NotNull @ManyToOne(fetch = FetchType.EAGER)
    private Lines destLine;
    @Column(name = "origin_number")
    @NotNull
    private String originNumber;
    @Column(name = "dest_number")
    @NotNull
    private String destNumber;
    @Column(name = "duration")
    @NotNull
    private Long duration;
    @Column(name = "total_price")
    @NotNull
    private double totalPrice;
    @Column(name = "call_date")
    @NotNull
    private Date callDate;
    @JoinColumn(name = "id_bill")
    @ManyToOne(fetch = FetchType.EAGER)
    private Bills bill;
}


