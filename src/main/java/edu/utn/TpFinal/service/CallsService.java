package edu.utn.TpFinal.service;

import edu.utn.TpFinal.Exceptions.CallAlreadyExists;
import edu.utn.TpFinal.Exceptions.ClientNotExists;
import edu.utn.TpFinal.Exceptions.LineNotExists;
import edu.utn.TpFinal.Projections.TopCalls;
import edu.utn.TpFinal.Projections.UserCalls;
import edu.utn.TpFinal.model.Calls;
import edu.utn.TpFinal.repository.CallsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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

    public Calls addCall(final Calls call) throws CallAlreadyExists, LineNotExists {
        //callsRepository.existsByOriginNumberAndCallDate(call.getOriginNumber(), call.getCallDate()).orElseThrow(()-> new CallAlreadyExists());
        if(callsRepository.existsByOriginNumberAndCallDate(call.getOriginNumber(), call.getCallDate()))
            throw new CallAlreadyExists();
        if(!linesService.existsByLineNumber(call.getOriginNumber()))
            throw new LineNotExists();
        return callsRepository.save(call);
    }

    public Page<Calls> getCalls(Pageable pageable){
        return callsRepository.findAll(pageable);
    }

    public Page<UserCalls> getUserCalls(Pageable pageable, Integer clientId, Integer lineId, Timestamp from, Timestamp to) throws LineNotExists, ClientNotExists {
        linesService.verifyClientAndLine(clientId, lineId);
        return callsRepository.getCallsByUser(pageable,lineId, from, to);
    }

    public List<TopCalls> findTop10Calls(Integer idClient, Integer idLine) throws LineNotExists, ClientNotExists {
        linesService.verifyClientAndLine(idClient, idLine);
        return callsRepository.getFavouritesCalls(idLine);
    }
}