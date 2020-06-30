package edu.utn.TpFinal.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallsDTO {
    @JsonProperty
    private String originNumber;
    @JsonProperty
    private String destNumber;
    @JsonProperty
    private Integer duration;
    @JsonProperty
    private Timestamp callDate;
}
