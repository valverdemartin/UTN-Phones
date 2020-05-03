package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.model.Cities;
import edu.utn.TpFinal.service.CitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {
    private CitiesService citiesService;

    @Autowired
    public CityController(CitiesService citiesService) {
        this.citiesService = citiesService;
    }

    @GetMapping("/all/")
    public List<Cities> getCities(){
        return citiesService.getCities();
    }

    @PostMapping("/")
    public void addCities(@RequestBody @Valid final Cities city){
        citiesService.addCity(city);
    }
}
