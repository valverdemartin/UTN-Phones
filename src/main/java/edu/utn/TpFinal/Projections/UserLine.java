package edu.utn.TpFinal.Projections;

import edu.utn.TpFinal.model.Lines;

public interface UserLine {
    String getPhoneNumber();
    String getType();

     void setPhoneNumber(String number);
     void setType(String type);
}
