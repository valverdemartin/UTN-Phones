package edu.utn.TpFinal.service;
import edu.utn.TpFinal.Exceptions.UserNotExist;
import edu.utn.TpFinal.Projections.TopCalls;
import edu.utn.TpFinal.Projections.UserCalls;
import edu.utn.TpFinal.model.Calls;
import edu.utn.TpFinal.repository.CallsRepository;
import edu.utn.TpFinal.repository.LinesRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.util.List;

@Service

public class CallsService {

    private CallsRepository callsRepository;
    private LinesService linesService;

    @Autowired
    public CallsService(CallsRepository callsRepository, LinesService linesService) {
        this.callsRepository = callsRepository;
        this.linesService = linesService;
    }

    public void addCall(final Calls call){
        callsRepository.save(call);
    }

    public List<Calls> getCalls(){
        return callsRepository.findAll();
    }

    public List<UserCalls> getUserCalls(Integer clientId, Integer lineId, Date from, Date to) {
        linesService.verifyClientAndLine(clientId, lineId);
        return callsRepository.getCallsByUser(lineId, from, to);
    }

    public List<TopCalls> findTop10Calls(Integer idClient, Integer idLine){
        linesService.verifyClientAndLine(idClient, idLine);
        return callsRepository.getFavouritesCalls(idLine);
    }
}