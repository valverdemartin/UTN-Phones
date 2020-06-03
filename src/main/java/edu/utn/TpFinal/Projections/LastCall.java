package edu.utn.TpFinal.Projections;

import edu.utn.TpFinal.model.Lines;
import edu.utn.TpFinal.model.Rates;

import java.sql.Date;

public interface LastCall {
    public Rates getRate();

    public Lines getOriginLine();

    public Lines getDestLine();

    public String getOriginNumber();

    public String getDestNumber();

    public Integer getDuration();

    public Double getTotalPrice();

    public Date getCallDate();
}