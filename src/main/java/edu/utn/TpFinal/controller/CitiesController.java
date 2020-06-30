package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.service.CitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CitiesController {
    private CitiesService citiesService;

    @Autowired
    public CitiesController(CitiesService citiesService){
        this.citiesService = citiesService;
    }

    /*@GetMapping("/")
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

    @PutMapping("/{id}/")
    public ResponseEntity updateCity(@RequestBody @Valid final Cities city, @PathVariable Integer id, @RequestParam(value="active", required = false) boolean active) throws CityNotExists, CityNameAlreadyExists, CityShortNameAlreadyExists, CityPrefixAlreadyExists, ProvinceNotExist, CityAlreadyActive, DeletionNotAllowed, CityAlreadyDeleted {
        return ResponseEntity.ok(citiesService.updateCity(city, id, active));
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity deleteCityById(@PathVariable Integer id) throws CityNotExists, CityAlreadyDeleted {
        citiesService.deleteCity(id);
        return ResponseEntity.status(200).build();
    }*/
}
