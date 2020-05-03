package edu.utn.TpFinal.service;

import edu.utn.TpFinal.model.Countries;
import edu.utn.TpFinal.repository.CountriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service

public class CountriesService {

    private CountriesRepository countriesRepository;

    @Autowired
    public CountriesService(CountriesRepository countriesRepository) {
        this.countriesRepository = countriesRepository;
    }

    public void addCountry(final Countries country){
        countriesRepository.save(country);
    }

    public List<Countries> getCountries(){
        return countriesRepository.findAll();
    }
}
