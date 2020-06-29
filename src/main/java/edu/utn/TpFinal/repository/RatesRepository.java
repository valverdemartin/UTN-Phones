package edu.utn.TpFinal.repository;

import edu.utn.TpFinal.model.Cities;
import edu.utn.TpFinal.model.Rates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;


public interface RatesRepository extends JpaRepository <Rates, Integer>{

    boolean existsByOriginCityAndDestCityAndIdNot(Cities originCity, Cities destCity, Integer id);

    boolean existsByOriginCityAndDestCityAndRateDate(Cities originCity, Cities destCity, Timestamp now);

    Boolean existsByOriginCityAndDestCity(Cities cityO, Cities cityD);
}
