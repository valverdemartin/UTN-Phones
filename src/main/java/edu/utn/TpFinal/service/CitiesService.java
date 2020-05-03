package edu.utn.TpFinal.service;

import edu.utn.TpFinal.model.Cities;
import edu.utn.TpFinal.model.Provinces;
import edu.utn.TpFinal.repository.CitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CitiesService {

    private CitiesRepository citiesRepository;

    @Autowired
    public CitiesService(CitiesRepository citiesRepository) {
        this.citiesRepository = citiesRepository;
    }

    public void addCity(final Cities city){
        citiesRepository.save(city);
    }

    public List<Cities> getCities(){
        return citiesRepository.findAll();
    }
}
