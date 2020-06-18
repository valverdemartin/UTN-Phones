package edu.utn.TpFinal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@Table( name= "phone_lines")

public class Lines {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "line_number")
    @NotNull
    private String phoneNumber;

    public enum Type {
        MOBILE, RESIDENTIAL;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "line_type")
    @NotNull
    private Type type;
    @JoinColumn(name = "id_city")
    @ManyToOne
    //@JsonBackReference
    @NotNull
    private Cities city;
    @JoinColumn(name = "id_client")
    @ManyToOne
    @JsonIdentityReference
    private Clients client;

    public enum Status {
        ACTIVE, SUSPENDED, CANCELLED;
    }
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @NotNull
    private Status status;
}
