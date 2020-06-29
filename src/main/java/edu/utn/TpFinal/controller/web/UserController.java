package edu.utn.TpFinal.controller.web;


import edu.utn.TpFinal.Exceptions.BillNotExists;
import edu.utn.TpFinal.Exceptions.ClientNotExists;
import edu.utn.TpFinal.Exceptions.LineNotExists;
import edu.utn.TpFinal.Projections.TopCalls;
import edu.utn.TpFinal.Projections.UserBills;
import edu.utn.TpFinal.Projections.UserCalls;
import edu.utn.TpFinal.controller.BillsController;
import edu.utn.TpFinal.controller.CallsController;
import edu.utn.TpFinal.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/userController")
public class UserController {

    CallsController callsController;
    BillsController billsController;
    SessionManager sessionManager;

    @Autowired
    public UserController(CallsController callsController, BillsController billsController, SessionManager sessionManager){
        this.callsController = callsController;
        this.billsController = billsController;
        this.sessionManager = sessionManager;

    }
                                                    /*Calls*/
    @GetMapping("/me/lines/{lineId}/calls/")
    public ResponseEntity<Page<UserCalls>> getUsersCalls(@RequestHeader("Authorization") String token,
                                                        @PageableDefault(page=0, size=5) Pageable pageable,
                                                        @PathVariable Integer lineId,
                                                         @RequestParam(required = false) Timestamp from,
                                                         @RequestParam(required = false)  Timestamp to)
                                            throws LineNotExists, ClientNotExists {
        Integer clientId = sessionManager.getCurrentUserDTO(token).getId();
        Page<UserCalls> calls = callsController.getUserCalls(pageable, clientId, lineId, from, to);
        return calls.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.ok(calls);
    }

                                                    /*Bills*/

    @GetMapping("/me/lines/{lineId}/bills/")
    public ResponseEntity<Page<UserBills>> getUserBills(@RequestHeader("Authorization") String token,
                                                        @PageableDefault(page=0, size=5) Pageable pageable,
                                                        @PathVariable Integer lineId,
                                                        @RequestParam(required = false) Timestamp from,
                                                        @RequestParam(required = false) Timestamp to) throws LineNotExists, BillNotExists, ClientNotExists {
        Integer clientId = sessionManager.getCurrentUserDTO(token).getId();
        Page<UserBills> calls = billsController.getUserBills(pageable,clientId, lineId, from, to);
        return calls.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.ok(calls);
    }

    @GetMapping("/top/me/lines/{lineId}/")
    public ResponseEntity<List<TopCalls>> findTop10Calls(@RequestHeader("Authorization") String token, @PathVariable Integer lineId) throws LineNotExists, ClientNotExists {
        Integer clientId = sessionManager.getCurrentUserDTO(token).getId();
        List<TopCalls> calls = callsController.findTop10Calls(clientId, lineId);
        return calls.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.ok(calls);
    }

}
