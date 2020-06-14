package edu.utn.TpFinal.Projections;

import java.sql.Date;

public interface UserCalls {
    String getDestNumber();
    Integer getDuration();
    Double getTotalPrice();
    Date getCallDate();
}
