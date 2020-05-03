package edu.utn.TpFinal.controller;
import edu.utn.TpFinal.model.Calls;
import edu.utn.TpFinal.service.CallsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/calls")
public class CallsController {
    private CallsService callsService;

    @Autowired
    public CallsController(CallsService CallsService) {
        this.callsService = CallsService;
    }

    @GetMapping("/all/")
    public List<Calls> getCalls(){
        return callsService.getCalls();
    }

    @PostMapping("/")
    public void addCall(@RequestBody @Valid final Calls call){
        callsService.addCall(call);
    }
}
