package edu.utn.TpFinal.service;

import edu.utn.TpFinal.model.Employees;
import edu.utn.TpFinal.model.Lines;
import edu.utn.TpFinal.repository.LinesRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class LinesService {

    private LinesRespository linesRespository;

    @Autowired
    public LinesService(LinesRespository linesRespository) {
        this.linesRespository = linesRespository;
    }

    public void addLine(final Lines line){
        linesRespository.save(line);
    }

    public List<Lines> getLines(){
        return linesRespository.findAll();
    }
}
