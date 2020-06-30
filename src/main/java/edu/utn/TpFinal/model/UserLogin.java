package edu.utn.TpFinal.model;

import lombok.*;

@Builder
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

public class UserLogin {
    private int id;
    String userType;
}
