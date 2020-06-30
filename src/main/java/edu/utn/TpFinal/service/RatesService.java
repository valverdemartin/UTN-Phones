package edu.utn.TpFinal.service;

import edu.utn.TpFinal.Exceptions.CityNotExists;
import edu.utn.TpFinal.Exceptions.RateAlreadyExists;
import edu.utn.TpFinal.Exceptions.RateNotExists;
import edu.utn.TpFinal.model.Cities;
import edu.utn.TpFinal.model.DTO.RateDTO;
import edu.utn.TpFinal.model.Rates;
import edu.utn.TpFinal.repository.CitiesRepository;
import edu.utn.TpFinal.repository.RatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service

public class RatesService {

    private RatesRepository ratesRepository;
    private CitiesRepository citiesRepository;

    @Autowired
    public RatesService(RatesRepository ratesRepository, CitiesRepository citiesRepository) {
        this.ratesRepository = ratesRepository;
        this.citiesRepository = citiesRepository;
    }

    public Rates addRate(final RateDTO rate) throws RateAlreadyExists, CityNotExists {
        if(!citiesRepository.existsById(rate.getOriginCity().getId()) || !citiesRepository.existsById(rate.getDestCity().getId()))
            throw new CityNotExists();
        if(ratesRepository.existsByOriginCityAndDestCityAndRateDate(rate.getOriginCity(), rate.getDestCity(), Timestamp.valueOf(LocalDateTime.now())))
            throw new RateAlreadyExists();
        Rates r = Rates.builder().costPrice(rate.getCostPrice())
                .destCity(rate.getDestCity())
                .originCity(rate.getOriginCity())
                .pricePerMinute(rate.getPricePerMinute())
                .rateDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        return ratesRepository.save(r);
    }

    public Page<Rates> getRates(Pageable pageable){
        return ratesRepository.findAll(pageable);
    }

    public Rates getRatesById(Integer rateId) throws RateNotExists {
        return ratesRepository.findById(rateId).orElseThrow(()-> new RateNotExists());
    }

    public Boolean existsByOriginLineAndDestLine(Cities cityO, Cities cityD) {
        return ratesRepository.existsByOriginCityAndDestCity(cityO, cityD);
    }
}
