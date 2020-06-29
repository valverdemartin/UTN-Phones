package edu.utn.TpFinal.controller.web;

import edu.utn.TpFinal.Exceptions.CallAlreadyExists;
import edu.utn.TpFinal.Exceptions.InvalidPhoneNumber;
import edu.utn.TpFinal.Exceptions.LineNotActive;
import edu.utn.TpFinal.Exceptions.LineNotExists;
import edu.utn.TpFinal.config.Configuration;
import edu.utn.TpFinal.controller.CallsController;
import edu.utn.TpFinal.model.Calls;
import edu.utn.TpFinal.model.DTO.CallsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/infraestructura")
public class InfraestructuraController {

    private CallsController callsController;

    @Autowired
    public InfraestructuraController(CallsController callsController){
        this.callsController = callsController;
    }

    @PostMapping("/calls/")
    public ResponseEntity<Calls> addCall(@RequestBody @Valid CallsDTO call) throws CallAlreadyExists, LineNotExists, LineNotActive, InvalidPhoneNumber {
        URI uri = Configuration.UriGenerator.getLocation(callsController.addCall(call).getId());
        return ResponseEntity.created(uri).build();
    }
}
