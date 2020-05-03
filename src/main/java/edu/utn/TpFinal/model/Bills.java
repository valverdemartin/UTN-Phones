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
public class Bills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Lines line;
    @NotNull
    private Integer callCounter;
    @NotNull
    private Double totalPrice;
    @NotNull
    private Double priceCost;
    @NotNull
    private Date billDate;
    @NotNull
    private Date dueDate;
}