package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.model.Provinces;
import edu.utn.TpFinal.service.ProvincesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/provinces")
public class ProvincesController{

    private ProvincesService provinceService;

    @Autowired
    public ProvincesController(ProvincesService provinceService) {
        this.provinceService = provinceService;
    }

    @GetMapping("/all/")
    public List<Provinces> getProvinces(){
        return provinceService.getProvinces();
    }

    @PostMapping("/")
    public void addProvince(@RequestBody @Valid final Provinces province){
        provinceService.addProvince(province);
    }
}
