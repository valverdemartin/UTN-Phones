package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Exceptions.BillNotExists;
import edu.utn.TpFinal.model.Bills;
import edu.utn.TpFinal.service.BillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/bills")
public class BillsController {
    private BillsService billsService;

    @Autowired
    public BillsController(BillsService BillsService) {
        this.billsService = BillsService;
    }

    @GetMapping("/")
    public ResponseEntity<Page<Bills>> getBills(@PageableDefault(page=0, size=5) Pageable pageable){
        Page<Bills> bills = billsService.getBills(pageable);
        return bills.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.ok(bills);
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity deleteBillById(@PathVariable Integer id) throws BillNotExists {
        billsService.deleteBill(id);
        return ResponseEntity.status(200).build();
    }

    /*@GetMapping("/{clientId}/{lineId}/")
    public ResponseEntity<List<UserCalls>> getUsersCalls(@PathVariable Integer clientId, @PathVariable Integer lineId, @RequestParam Date from, @RequestParam Date to){
        List<UserCalls> calls = callsService.getUserCalls(clientId, lineId, from, to);
        return calls.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.ok(calls);
    }*/
    //ToDo 			□ Refactorizar para BIlls
    //ToDo 			□ Consulta de Bills Debe ser por rango de fecha de user logueado
}
