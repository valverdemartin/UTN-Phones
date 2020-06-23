package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Exceptions.RateAlreadyExists;
import edu.utn.TpFinal.Exceptions.RateNotExists;
import edu.utn.TpFinal.model.Rates;
import edu.utn.TpFinal.service.RatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/rates")
public class RatesController {
    private RatesService ratesService;

    @Autowired
    public RatesController(RatesService ratesService) {
        this.ratesService = ratesService;
    }

    @GetMapping("/")
    public ResponseEntity<Page<Rates>> getRates(@PageableDefault(page=0, size=5) Pageable pageable){
        Page<Rates> rates = ratesService.getRates(pageable);
        return rates.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.ok(rates);
    }

    @GetMapping("/{rateId}/")
    public ResponseEntity<Rates> getRates(@PathVariable Integer rateId) throws RateNotExists {
        Rates rate = (Rates) ratesService.getRatesById(rateId);
        return ResponseEntity.ok(rate);
    }

    @PostMapping("/")
    public void addRate(@RequestBody @Valid final Rates rate) throws RateAlreadyExists {
        ratesService.addRate(rate);
    }


}
