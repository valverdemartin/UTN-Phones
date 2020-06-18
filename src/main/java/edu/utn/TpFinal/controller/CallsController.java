package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Exceptions.CallAlreadyExists;
import edu.utn.TpFinal.Exceptions.ClientNotExists;
import edu.utn.TpFinal.Exceptions.LineNotExists;
import edu.utn.TpFinal.Projections.TopCalls;
import edu.utn.TpFinal.Projections.UserCalls;
import edu.utn.TpFinal.model.Calls;
import edu.utn.TpFinal.service.CallsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
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
    public ResponseEntity<Page<Calls>> getCalls(@PageableDefault(page=0, size=5) Pageable pageable) {
        Page<Calls> calls = callsService.getCalls(pageable);
        return calls.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.ok(calls);
    }

    @PostMapping("/")
    public ResponseEntity<Calls> addCall(@RequestBody @Valid Calls call) throws CallAlreadyExists, LineNotExists {
        return ResponseEntity.ok(callsService.addCall(call));
    }
    //ToDo verificar UTC

    @GetMapping("/{clientId}/{lineId}/")
    public ResponseEntity<Page<UserCalls>> getUsersCalls(@PageableDefault(page=0, size=5) Pageable pageable,
                                                         @PathVariable Integer clientId, @PathVariable Integer lineId,
                                                         @RequestParam Timestamp from, @RequestParam Timestamp to)
                                                            throws LineNotExists, ClientNotExists {
        Page<UserCalls> calls = callsService.getUserCalls(pageable, clientId, lineId, from, to);
        System.out.println(calls.getTotalPages());
        return calls.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.ok(calls);
    }//ToDo 			□ Consulta de llamadas Debe ser por rango de fecha de user logueado

    @GetMapping("/top/{clientId}/{lineId}/")
    public ResponseEntity<List<TopCalls>> findTop10Calls(@PathVariable Integer lineId, @PathVariable Integer clientId) throws LineNotExists, ClientNotExists {
        List<TopCalls> calls = callsService.findTop10Calls(clientId, lineId);
        return calls.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.ok(calls);
    }//ToDo          □Consulta de destinos más llamados por usuario logueado








}