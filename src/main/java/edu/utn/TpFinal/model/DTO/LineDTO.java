package edu.utn.TpFinal.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.utn.TpFinal.model.Cities;
import edu.utn.TpFinal.model.Lines;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LineDTO {
    @JsonProperty
    private String phoneNumber;
    @JsonProperty
    private Lines.Type type;
    @JsonProperty
    private Cities city;
    @JsonProperty
    private Lines.Status status;
}
