package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Projections.UserCalls;
import edu.utn.TpFinal.model.Bills;
import edu.utn.TpFinal.service.BillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
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

    /*@GetMapping("/{clientId}/{lineId}/")
    public ResponseEntity<List<UserCalls>> getUsersCalls(@PathVariable Integer clientId, @PathVariable Integer lineId, @RequestParam Date from, @RequestParam Date to){
        List<UserCalls> calls = callsService.getUserCalls(clientId, lineId, from, to);
        return calls.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.ok(calls);
    }*/
    //ToDo 			□ Refactorizar para BIlls
    //ToDo 			□ Consulta de Bills Debe ser por rango de fecha de user logueado
}
