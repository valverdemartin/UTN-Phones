package edu.utn.TpFinal.service;
import edu.utn.TpFinal.Exceptions.*;
import edu.utn.TpFinal.model.Cities;
import edu.utn.TpFinal.model.Provinces;
import edu.utn.TpFinal.repository.ProvincesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service

public class ProvincesService {

    private ProvincesRepository provincesRepository;

    @Autowired
    public ProvincesService(ProvincesRepository provincesRepository) {
        this.provincesRepository = provincesRepository;
    }

    public Provinces addProvince(final Provinces province) throws ProvinceNameAlreadyExists {
        if(provincesRepository.existsByName(province.getName()))
            throw new ProvinceNameAlreadyExists();
        return provincesRepository.save(province);
    }

    public Page<Provinces> getProvinces(Pageable pageable){
        return provincesRepository.findAll(pageable);
    }

    public Provinces getProvincesById(Integer id) throws ProvinceNotExist {
        return provincesRepository.findById(id).orElseThrow(()-> new ProvinceNotExist());
    }

    public Object updateProvinces(Provinces province, Integer id, Boolean active) throws ProvinceNameAlreadyExists, ProvinceNotExist, DeletionNotAllowed, ProvinceAlreadyActive, ProvinceAlreadyDeleted {
        Provinces p = provinceValidationUpdate(province, id);
        province.setId(id);
        province.setCities(p.getCities());
        if(province.getActive()){
            if(active!= null && active)
                activeValidationProvince(province.getId(), true);
        }else{
            throw new DeletionNotAllowed();
        }
        return provincesRepository.save(province);
    }

    public void deleteProvince(Integer id) throws ProvinceAlreadyDeleted, ProvinceNotExist, ProvinceIsNotEmpty, ProvinceNameAlreadyExists {
        Provinces p = provinceValidation(id);
        if(!p.getActive()){
            throw new ProvinceAlreadyDeleted();
        }
        if(!p.getCities().isEmpty())
            throw new ProvinceIsNotEmpty();
        p.setActive(Boolean.FALSE);
        provincesRepository.save(p);
    }

    ///////////////////////////Validations////////////////////////////////////////////////
    public Provinces provinceValidation(Integer id) throws ProvinceNameAlreadyExists, ProvinceNotExist {
        Provinces p = provincesRepository.findById(id).orElseThrow(()-> new ProvinceNotExist());
        if(provincesRepository.existsByName(p.getName()))
            throw new ProvinceNameAlreadyExists();
        return p;
    }

    public Provinces provinceValidationUpdate(Provinces provinces, Integer id) throws ProvinceNameAlreadyExists, ProvinceNotExist {
        Provinces p = provincesRepository.findById(id).orElseThrow(()-> new ProvinceNotExist());
        if(provincesRepository.existsByNameAndIdNot(provinces.getName(), id))
            throw new ProvinceNameAlreadyExists();
        return provinces;
    }

    public void activeValidationProvince(Integer id, Boolean active) throws ProvinceAlreadyDeleted, ProvinceAlreadyActive {
        Provinces provinces = provincesRepository.findById(id).get();
        if(!active && !provinces.getActive())
            throw new ProvinceAlreadyDeleted();
        if(active && provinces.getActive())
            throw new ProvinceAlreadyActive();
    }

    //////////////////////////////End Validations////////////////////////////////////////////

}
