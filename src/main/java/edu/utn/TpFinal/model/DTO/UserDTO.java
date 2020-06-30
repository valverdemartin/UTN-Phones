package edu.utn.TpFinal.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDTO {
    @JsonProperty
    private String name;
    @JsonProperty
    private String lastName;
    @JsonProperty
    private Integer dni;
    @JsonProperty
    private String userName;
    @JsonProperty
    private String password;
    @JsonProperty
    private Boolean active;
}
