package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Exceptions.RateAlreadyExists;
import edu.utn.TpFinal.Exceptions.RateNotExists;
import edu.utn.TpFinal.model.Rates;
import edu.utn.TpFinal.service.RatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/")
    public void addRate(@RequestBody @Valid final Rates rate) throws RateAlreadyExists {
        ratesService.addRate(rate);
    }


}
