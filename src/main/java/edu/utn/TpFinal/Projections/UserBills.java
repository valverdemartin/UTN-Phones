package edu.utn.TpFinal.Projections;

import edu.utn.TpFinal.model.Lines;

import java.sql.Date;

public interface UserBills {
    Lines getLine();
    Integer getCallCounter();
    Double getTotalPrice();
    Double getCostPrice();
    Date getBillDate();
    Date getDueDate();
}
