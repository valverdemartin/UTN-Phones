package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Exceptions.CityNotExists;
import edu.utn.TpFinal.Exceptions.RateAlreadyExists;
import edu.utn.TpFinal.Exceptions.RateNotExists;
import edu.utn.TpFinal.model.DTO.RateDTO;
import edu.utn.TpFinal.model.Rates;
import edu.utn.TpFinal.service.RatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class RatesController {
    private RatesService ratesService;

    @Autowired
    public RatesController(RatesService ratesService) {
        this.ratesService = ratesService;
    }


    public Page<Rates> getRates(Pageable pageable){
        return ratesService.getRates(pageable);
    }


    public Rates getRatesById(Integer rateId) throws RateNotExists {
        return ratesService.getRatesById(rateId);
    }


    public Rates addRate(@RequestBody @Valid final RateDTO rate) throws RateAlreadyExists, CityNotExists {
        return ratesService.addRate(rate);
    }
}
