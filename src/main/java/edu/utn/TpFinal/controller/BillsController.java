package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Exceptions.BillNotExist;
import edu.utn.TpFinal.model.Bills;
import edu.utn.TpFinal.service.BillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/bills")
public class BillsController {
    private BillsService billsService;

    @Autowired
    public BillsController(BillsService BillsService) {
        this.billsService = BillsService;
    }

    @GetMapping("/")
    public List<Bills> getBills(){
        return billsService.getBills();
    }

    @PostMapping("/")
    public void addBill(@RequestBody @Valid final Bills bill){
        billsService.addBill(bill);
    }

    @DeleteMapping("/{id}/")
    public void deleteBillById(@PathVariable Integer id) {
        billsService.deleteBill(id);
    }
}
