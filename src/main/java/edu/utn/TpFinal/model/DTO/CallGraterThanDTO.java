package edu.utn.TpFinal.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.io.Serializable;
import java.sql.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallGraterThanDTO implements Serializable {
    private String destNumber;
    private Integer duration;
    private Double totalPrice;
    private Date callDate;
}
