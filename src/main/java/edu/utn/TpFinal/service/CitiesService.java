package edu.utn.TpFinal.service;

import edu.utn.TpFinal.Exceptions.*;
import edu.utn.TpFinal.model.Cities;
import edu.utn.TpFinal.repository.CitiesRepository;
import edu.utn.TpFinal.repository.ProvincesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service

public class CitiesService {

    private CitiesRepository citiesRepository;
    private ProvincesRepository provincesRepository;

    @Autowired
    public CitiesService(CitiesRepository citiesRepository, ProvincesRepository provincesRepository) {
        this.citiesRepository = citiesRepository;
        this.provincesRepository = provincesRepository;
    }

    public Cities addCity(final Cities city) throws CityPrefixAlreadyExists, CityNameAlreadyExists, CityShortNameAlreadyExists, ProvinceNotExist {
        cityValidation(city);
        return citiesRepository.save(city);
    }

    public Page<Cities> getCities(Pageable pageable){
        return citiesRepository.findByActive(pageable, true);
    }

    public void deleteCity(Integer id) throws CityNotExists, AlreadyDeleted {
        Cities city = citiesRepository.findById(id).orElseThrow(() -> new CityNotExists());
        if(!city.getActive()){
            throw new AlreadyDeleted();
        }
        city.setActive(Boolean.FALSE);
        citiesRepository.save(city);
    }

    public void cityValidation(Cities city) throws CityPrefixAlreadyExists, CityNameAlreadyExists, CityShortNameAlreadyExists, ProvinceNotExist {
        if(citiesRepository.existsByName(city.getName()))
            throw new CityNameAlreadyExists();
        if(citiesRepository.existsByShortName(city.getShortName()))
            throw new CityShortNameAlreadyExists();
        if(citiesRepository.existsByPrefix(city.getPrefix()))
            throw new CityPrefixAlreadyExists();
        if(!provincesRepository.existsById(city.getProvince().getId()))
            throw new ProvinceNotExist();
    }

}
