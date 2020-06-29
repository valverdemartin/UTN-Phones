package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Exceptions.BillNotExists;
import edu.utn.TpFinal.Exceptions.ClientNotExists;
import edu.utn.TpFinal.Exceptions.LineNotExists;
import edu.utn.TpFinal.Projections.UserBills;
import edu.utn.TpFinal.service.BillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.sql.Date;
import java.sql.Timestamp;


@Controller
public class BillsController {
    private BillsService billsService;

    @Autowired
    public BillsController(BillsService BillsService) {
        this.billsService = BillsService;
    }


    public ResponseEntity<UserBills> getUserBillById(Integer clientId,
                                                     Integer lineId,
                                                     Integer billId) throws LineNotExists, BillNotExists, ClientNotExists {
        return ResponseEntity.ok(billsService.getUserBillById(clientId, lineId, billId));
    }


    public Page<UserBills> getUserBills(Pageable pageable, Integer clientId,
                                        Integer lineId, Timestamp from, Timestamp to)
                                        throws LineNotExists, BillNotExists, ClientNotExists {
        return billsService.getUserBills(pageable,clientId, lineId, from, to);
    }


    public ResponseEntity deleteBillById(Integer id) throws BillNotExists {
        billsService.deleteBill(id);
        return ResponseEntity.status(200).build();
    }
    //ToDo 			□ Refactorizar para BIlls
    //ToDo 			□ Consulta de Bills Debe ser por rango de fecha de user logueado
}
