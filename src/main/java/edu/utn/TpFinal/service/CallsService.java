package edu.utn.TpFinal.service;
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

    public void addCall(final Calls calls){
        callsRepository.save(calls);
    }

    public List<Calls> getCalls(){
        return callsRepository.findAll();
    }
}