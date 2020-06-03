package edu.utn.TpFinal.service;

import edu.utn.TpFinal.Projections.LastCall;
import edu.utn.TpFinal.model.Calls;
import edu.utn.TpFinal.repository.CallsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CallsService {

    private CallsRepository callsRepository;

    @Autowired
    public CallsService(CallsRepository callsRepository) {
        this.callsRepository = callsRepository;
    }

    public void addCall(final Calls call){
        callsRepository.save(call);
    }

    public List<Calls> getCalls(){
        return callsRepository.findAll();
    }

    public List<LastCall> getLastsCalls(){return  callsRepository.getLastCalls();
    }

}