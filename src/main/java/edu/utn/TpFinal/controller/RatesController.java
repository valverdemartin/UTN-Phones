package edu.utn.TpFinal.controller;
import edu.utn.TpFinal.model.Rates;
import edu.utn.TpFinal.service.RatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rates")
public class RatesController {
    private RatesService ratesService;

    @Autowired
    public RatesController(RatesService ratesService) {
        this.ratesService = ratesService;
    }

    @GetMapping("/")
    public List<Rates> getRates(){
        return ratesService.getRates();
    }

    @PostMapping("/")
    public void addRate(@RequestBody @Valid final Rates rate){
        ratesService.addRate(rate);
    }
}
