package edu.utn.TpFinal.service;


import edu.utn.TpFinal.model.Provinces;
import edu.utn.TpFinal.repository.ProvincesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ProvincesService {

    private ProvincesRepository provincesRepository;

    @Autowired
    public ProvincesService(ProvincesRepository provincesRepository) {
        this.provincesRepository = provincesRepository;
    }

    public void addProvince(final Provinces province){
        provincesRepository.save(province);
    }

    public List<Provinces> getProvinces(){
        return provincesRepository.findAll();
    }
}
