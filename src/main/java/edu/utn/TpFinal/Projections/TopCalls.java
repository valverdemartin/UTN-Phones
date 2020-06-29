package edu.utn.TpFinal.Projections;

import java.sql.Date;

public interface TopCalls {
    String getDestNumber();
    Integer getCount();

    void setDestNumber(String destNumber);
    void setCount(Integer count);
}
