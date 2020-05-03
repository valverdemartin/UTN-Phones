package edu.utn.TpFinal.controller;


import ch.qos.logback.core.net.server.Client;
import edu.utn.TpFinal.model.Clients;
import edu.utn.TpFinal.service.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientsController {

    private final ClientsService clientsService;

    @Autowired
    public ClientsController(final ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @GetMapping("/id/")
    public Clients getClientsById(@RequestParam Integer clientId){
        return clientsService.getClientsById(clientId);
    }

    @GetMapping("/all/")
    public List<Clients> getClients(){
        return clientsService.getClients();
    }

    @GetMapping("/ln/")
    public Clients getClients(@RequestParam String lastName){
        return clientsService.getClientsByLastName(lastName);
    }


    @PostMapping("/")
    public void addClient(@RequestBody @Valid final Clients client){
        clientsService.addClient(client);
    }
}
