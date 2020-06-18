package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Exceptions.*;
import edu.utn.TpFinal.model.Cities;
import edu.utn.TpFinal.service.CitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cities")
public class CitiesController {
    private CitiesService citiesService;

    @Autowired
    public CitiesController(CitiesService citiesService) {
        this.citiesService = citiesService;
    }

    @GetMapping("/")
    public ResponseEntity<Page<Cities>>
    getCities(@PageableDefault(page=0, size=5) Pageable pageable)
    {
        Page<Cities> cities = citiesService.getCities(pageable);
        return cities.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.ok(cities);
    }

    @PostMapping("/")
    public ResponseEntity addCity(@RequestBody @Valid final Cities city) throws CityPrefixAlreadyExists, CityNameAlreadyExists, CityShortNameAlreadyExists, ProvinceNotExist {
        return ResponseEntity.ok(citiesService.addCity(city));
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity deleteCityById(@PathVariable Integer id) throws CityNotExists, AlreadyDeleted {
        citiesService.deleteCity(id);
        return ResponseEntity.status(200).build();
    }
}
