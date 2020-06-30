package edu.utn.TpFinal.service;

import edu.utn.TpFinal.repository.CitiesRepository;
import edu.utn.TpFinal.repository.ProvincesRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    /*public Cities addCity(final Cities city) throws CityPrefixAlreadyExists, CityNameAlreadyExists, CityShortNameAlreadyExists, ProvinceNotExist {
        cityValidation(city);
        return citiesRepository.save(city);
    }

    public Page<Cities> getCities(Pageable pageable){
        return citiesRepository.findByActiveTrue(pageable);
    }

    public void deleteCity(Integer id) throws CityNotExists, CityAlreadyDeleted {
        Cities city = citiesRepository.findById(id).orElseThrow(() -> new CityNotExists());
        if(!city.getActive()){
            throw new CityAlreadyDeleted();
        }
        city.setActive(Boolean.FALSE);
        citiesRepository.save(city);
    }

    public Cities updateCity(Cities city, Integer idCity, Boolean active) throws CityNameAlreadyExists, CityShortNameAlreadyExists, CityPrefixAlreadyExists, ProvinceNotExist, CityNotExists, CityAlreadyDeleted, CityAlreadyActive, DeletionNotAllowed {
        cityValidationUpdate(city, idCity);
        if(city.getActive()){
            if(active)
                activeValidationCity(city.getId(), true);
        }else{
            throw new DeletionNotAllowed();
        }
        return citiesRepository.save(city);
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

    public void cityValidationUpdate(Cities city, Integer idCity) throws CityNameAlreadyExists, CityShortNameAlreadyExists, CityPrefixAlreadyExists, ProvinceNotExist, CityNotExists {
        if(!citiesRepository.existsById(idCity))
            throw new CityNotExists();
        if(citiesRepository.existsByIdNotAndName(idCity, city.getName()))
            throw new CityNameAlreadyExists();
        if(citiesRepository.existsByShortNameAndIdNot(city.getShortName(), idCity))
            throw new CityShortNameAlreadyExists();
        if(citiesRepository.existsByPrefixAndIdNot(city.getPrefix(), idCity))
            throw new CityPrefixAlreadyExists();
        if(!provincesRepository.existsById(city.getProvince().getId()))
            throw new ProvinceNotExist();
    }

    public void activeValidationCity(Integer id, Boolean active) throws CityAlreadyDeleted, CityAlreadyActive {
        Cities city = citiesRepository.findById(id).get();
        if(!active && !city.getActive())
            throw new CityAlreadyDeleted();
        if(active && city.getActive())
            throw new CityAlreadyActive();
    }*/
}
