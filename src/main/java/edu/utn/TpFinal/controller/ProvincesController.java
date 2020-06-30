package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.service.ProvincesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ProvincesController{

    private ProvincesService provinceService;

    @Autowired
    public ProvincesController(ProvincesService provinceService) {
        this.provinceService = provinceService;
    }

    /*@GetMapping("/")
    public ResponseEntity<Page<Provinces>> getProvinces(@PageableDefault(page=0, size=5) Pageable pageable){
        Page<Provinces> provinces = provinceService.getProvinces(pageable);
        return provinces.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.ok(provinces);
    }

    @GetMapping("/{id}/")
    public ResponseEntity<Provinces> getProvinceById(@PathVariable Integer id) throws ProvinceNotExist {
        return ResponseEntity.ok(provinceService.getProvincesById(id));
    }

    @PostMapping("/")
    public ResponseEntity addProvince(@RequestBody @Valid final Provinces province) throws ProvinceNameAlreadyExists {
        return ResponseEntity.ok(provinceService.addProvince(province));
    }

    @PutMapping("/{id}/")
    public ResponseEntity updateProvince(@RequestBody @Valid final Provinces province, @PathVariable Integer id, @RequestParam(value="active", required = false) Boolean active) throws ProvinceNameAlreadyExists, ProvinceAlreadyActive, DeletionNotAllowed, ProvinceAlreadyDeleted, ProvinceNotExist {
        return ResponseEntity.ok(provinceService.updateProvinces(province, id, active));
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity deleteProvinceById(@PathVariable Integer id) throws ProvinceNotExist, ProvinceAlreadyDeleted, ProvinceIsNotEmpty, ProvinceNameAlreadyExists {
        provinceService.deleteProvince(id);
        return ResponseEntity.status(200).build();
    }*/

}
