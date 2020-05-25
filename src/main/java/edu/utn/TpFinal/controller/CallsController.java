package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.model.Calls;
import edu.utn.TpFinal.service.CallsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public void insertNewCall(@RequestParam String origin, String destiny, Long duration) {
        callsService.insertNewCall(origin, destiny, duration);
    }
}