package edu.utn.TpFinal.service;

import edu.utn.TpFinal.Exceptions.DeletionNotAllowed;
import edu.utn.TpFinal.Exceptions.RateAlreadyExists;
import edu.utn.TpFinal.Exceptions.RateNotExists;
import edu.utn.TpFinal.model.Rates;
import edu.utn.TpFinal.repository.RatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service

public class RatesService {

    private RatesRepository ratesRepository;

    @Autowired
    public RatesService(RatesRepository ratesRepository) {
        this.ratesRepository = ratesRepository;
    }

    public Rates addRate(final Rates rate) throws RateAlreadyExists {
        if(ratesRepository.existsByOriginCityAndDestCity(rate.getOriginCity(), rate.getDestCity()))
            throw new RateAlreadyExists();
        //rate.setRateDate((Date)Time.now());
        return ratesRepository.save(rate);
    }

    public Page<Rates> getRates(Pageable pageable){
        return ratesRepository.findAll(pageable);
    }

    public Rates getRatesById(Integer rateId) throws RateNotExists {
        return ratesRepository.findById(rateId).orElseThrow(()-> new RateNotExists());
    }

}
