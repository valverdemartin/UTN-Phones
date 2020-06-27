package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Exceptions.BillNotExists;
import edu.utn.TpFinal.Exceptions.ClientNotExists;
import edu.utn.TpFinal.Exceptions.LineNotExists;
import edu.utn.TpFinal.Projections.UserBills;
import edu.utn.TpFinal.model.Bills;
import edu.utn.TpFinal.service.BillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;


@RestController
@RequestMapping("/bills")
public class BillsController {
    private BillsService billsService;

    @Autowired
    public BillsController(BillsService BillsService) {
        this.billsService = BillsService;
    }

    @GetMapping("/{clientId}/{lineId}/{billId}/")
    public ResponseEntity<UserBills> getUserBillById(@PathVariable Integer clientId,
                                                     @PathVariable Integer lineId,
                                                     @PathVariable Integer billId) throws LineNotExists, BillNotExists, ClientNotExists {
        return ResponseEntity.ok(billsService.getUserBillById(clientId, lineId, billId));
    }

    @GetMapping("/{clientId}/{lineId}/")
    public ResponseEntity<Page<UserBills>> getUsersCalls(@PageableDefault(page=0, size=5) Pageable pageable,
                                                         @PathVariable Integer clientId,
                                                         @PathVariable Integer lineId,
                                                         @RequestParam(required = false)  Date from,
                                                         @RequestParam(required = false) Date to) throws LineNotExists, BillNotExists, ClientNotExists {
        Page<UserBills> calls = billsService.getUserBills(pageable,clientId, lineId, from, to);
        return calls.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.ok(calls);
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity deleteBillById(@PathVariable Integer id) throws BillNotExists {
        billsService.deleteBill(id);
        return ResponseEntity.status(200).build();
    }
    //ToDo 			□ Refactorizar para BIlls
    //ToDo 			□ Consulta de Bills Debe ser por rango de fecha de user logueado
}
