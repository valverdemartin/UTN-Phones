package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Projections.TopCalls;
import edu.utn.TpFinal.Projections.UserCalls;
import edu.utn.TpFinal.model.Calls;
import edu.utn.TpFinal.service.CallsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/calls")
public class CallsController {
    private CallsService callsService;

    @Autowired
    public CallsController(CallsService CallsService) {
        this.callsService = CallsService;
    }

    @GetMapping("/")
    public List<Calls> getCalls() {
        return callsService.getCalls();
    }

    @PostMapping("/")
    public void addCall(@RequestBody @Valid Calls call) {
        callsService.addCall(call);
    }

    @GetMapping("/{clientId}/{lineId}/")
    public ResponseEntity<List<UserCalls>> getUsersCalls(@PathVariable Integer clientId,@PathVariable Integer lineId, @RequestParam Date from, @RequestParam Date to){
        List<UserCalls> calls = callsService.getUserCalls(clientId, lineId, from, to);
        return calls.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.ok(calls);
    }//ToDo 			□ Consulta de llamadas Debe ser por rango de fecha de user logueado

    @GetMapping("/top/{clientId}/{lineId}/")
    public ResponseEntity<List<TopCalls>> findTop10Calls(@PathVariable Integer lineId, @PathVariable Integer clientId){
        List<TopCalls> calls = callsService.findTop10Calls(clientId, lineId);
        return calls.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.ok(calls);
    }//ToDo          □Consulta de destinos más llamados por usuario logueado








}