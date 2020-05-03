package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.model.Countries;
import edu.utn.TpFinal.service.CountriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/countries")
public class CountriesController {
    private CountriesService countriesService;

    @Autowired
    public CountriesController(CountriesService countriesService) {
        this.countriesService = countriesService;
    }

    @GetMapping("/all/")
    public List<Countries> getCountries(){
        return countriesService.getCountries();
    }

    @PostMapping("/")
    public void addCountry(@RequestBody @Valid final Countries country){
        countriesService.addCountry(country);
    }
}
