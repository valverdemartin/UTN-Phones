package edu.utn.TpFinal.Projections;


import java.sql.Timestamp;

public interface UserCalls {
    String getDestNumber();
    Integer getDuration();
    Double getTotalPrice();
    Timestamp getCallDate();

    void setDestNumber(String destNumb);
    void setDuration(Integer duration);
    void setTotalPrice(Double price);
    void setCallDate(Timestamp date);
}
