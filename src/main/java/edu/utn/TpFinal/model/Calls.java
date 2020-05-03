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
public class Calls {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull @ManyToOne(fetch = FetchType.EAGER)
    private Rates rate;
    @NotNull @ManyToOne(fetch = FetchType.EAGER)
    private Lines originLine;
    @NotNull @ManyToOne(fetch = FetchType.EAGER)
    private Lines destLine;
    @NotNull
    private long duration;
    @NotNull
    private double totalPrice;
    @NotNull
    private Date callDate;
    @NotNull
    private boolean invoiced;
    @ManyToOne(fetch = FetchType.EAGER)
    private Bills bill;
}

