package edu.utn.TpFinal.repository;
import edu.utn.TpFinal.model.Cities;
import edu.utn.TpFinal.model.Rates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RatesRepository extends JpaRepository <Rates, Integer>{

    boolean existsByOriginCityAndDestCityAndIdNot(Cities originCity, Cities destCity, Integer id);

    Boolean existsByOriginCityAndDestCity(Cities originCity, Cities destCity);
}
