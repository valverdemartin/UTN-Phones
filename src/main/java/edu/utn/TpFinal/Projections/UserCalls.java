package edu.utn.TpFinal.Projections;


import java.sql.Timestamp;

public interface UserCalls {
    String getDestNumber();
    Integer getDuration();
    Double getTotalPrice();
    Timestamp getCallDate();
}
