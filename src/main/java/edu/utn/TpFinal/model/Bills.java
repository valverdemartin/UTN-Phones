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
@Table( name= "bills")

public class Bills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JoinColumn(name = "id_line")
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Lines line;
    @Column(name = "calls_count")
    @NotNull
    private Integer callCounter;
    @Column(name = "total_price")
    @NotNull
    private Double totalPrice;
    @Column(name = "cost_price")
    @NotNull
    private Double costPrice;
    @Column( name= "bill_date")
    @NotNull
    private Date billDate;
    @Column(name = "due_date")
    @NotNull
    private Date dueDate;
}