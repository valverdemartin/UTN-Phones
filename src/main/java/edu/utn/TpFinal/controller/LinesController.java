package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.model.Lines;
import edu.utn.TpFinal.service.LinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/lines")
public class LinesController {
    private LinesService linesService;

    @Autowired
    public LinesController(LinesService linesService) {
        this.linesService = linesService;
    }

    @GetMapping("/")
    public List<Lines> getLines(){
        return linesService.getLines();
    }

    @PostMapping("/")
    public void addLine(@RequestBody @Valid final Lines line){
        linesService.addLine(line);
    }
}


//ToDo enum con tipo de línea.
//ToDo verificar "active" sin valor x defecto.