package edu.utn.TpFinal.model;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.sql.Date;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Table(name = "calls")

public class Calls {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JoinColumn(name = "id_rate")
    @ManyToOne(fetch = FetchType.EAGER)
    private Rates rate;
    @JoinColumn(name = "id_origin_line")
    @ManyToOne(fetch = FetchType.EAGER)
    private Lines originLine;
    @JoinColumn(name = "id_dest_line")
    @ManyToOne(fetch = FetchType.EAGER)
    private Lines destLine;
    @Column(name = "origin_number")
    @NotNull
    private String originNumber;
    @Column(name = "dest_number")
    @NotNull
    private String destNumber;
    @Column(name = "duration")
    @NotNull
    private Integer duration;
    @Column(name = "total_price")
    private Double totalPrice;
    @Column(name = "call_date")
    @NotNull
    private Date callDate;
    @JoinColumn(name = "id_bill")
    @ManyToOne(fetch = FetchType.EAGER)
    private Bills bill;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Calls)) return false;
        Calls calls = (Calls) o;
        return id.equals(calls.id) &&
                originLine.equals(calls.originLine) &&
                destLine.equals(calls.destLine) &&
                duration.equals(calls.duration) &&
                callDate.equals(calls.callDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, originLine, destLine, duration, callDate);
    }

}


