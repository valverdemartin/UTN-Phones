package edu.utn.TpFinal.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.utn.TpFinal.model.Cities;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class RateDTO {
    @JsonProperty
    private Cities originCity;
    @JsonProperty
    private Cities destCity;
    @JsonProperty
    private double pricePerMinute;
    @JsonProperty
    private Timestamp rateDate;
    @JsonProperty
    private Double costPrice;
}
