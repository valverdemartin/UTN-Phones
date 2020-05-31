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
    @Column(name = "line_type")
    @NotNull
    private String type; // Hacer Enum movil o residencial
    @JoinColumn(name = "id_city")
    @ManyToOne
    @JsonBackReference
    @NotNull
    private Cities city;
    @JoinColumn(name = "id_client")
    @ManyToOne
    @JsonIdentityReference
    //@JsonManagedReference
    private Clients client;
    @Column(name = "active")
    private Boolean active;

}
