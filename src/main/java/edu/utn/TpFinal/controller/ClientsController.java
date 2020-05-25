package edu.utn.TpFinal.controller;

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

    @GetMapping("/{clientId}/")
    public Clients getClientsById(@PathVariable Integer clientId){
        return clientsService.getClientsById(clientId);
    }

    @GetMapping("/")
    public List<Clients> getClients(){
        return clientsService.getClients();
    }

    @GetMapping("/ln/{lastName}/")
    public Clients getClients(@PathVariable String lastName){
        return clientsService.getClientsByLastName(lastName);
    }

    @PostMapping("/")
    public void addClient(@RequestBody @Valid final Clients client){
        clientsService.addClient(client);
    }

    @PutMapping("/{clientId}/")
    public void activeClient(@PathVariable Integer clientId){
        this.clientsService.putActive(clientId);
    }
}


//ToDo DTO persona con líneas.