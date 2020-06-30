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


    public void setLine(Lines line);

    public void setCallCounter(Integer callCounter);

    public void setTotalPrice(Double totalPrice);

    public void setCostPrice(Double costPrice);

    public void setBillDate(Date billDate);

    public void setDueDate(Date dueDate);

    public void setActive(Boolean active);

}
