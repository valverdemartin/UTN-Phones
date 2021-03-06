package edu.utn.TpFinal.service;

import edu.utn.TpFinal.Exceptions.*;
import edu.utn.TpFinal.Projections.TopCalls;
import edu.utn.TpFinal.Projections.UserCalls;
import edu.utn.TpFinal.model.Calls;
import edu.utn.TpFinal.model.DTO.CallsDTO;
import edu.utn.TpFinal.model.Lines;
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
    private RatesService ratesService;

    @Autowired
    public CallsService(CallsRepository callsRepository, LinesService linesService, RatesService ratesService) {
        this.callsRepository = callsRepository;
        this.linesService = linesService;
        this.ratesService =  ratesService;
    }

    public Calls addCall(final CallsDTO call) throws CallAlreadyExists, LineNotExists, LineNotActive, InvalidPhoneNumber, RateNotExists {
        verifyCallLines(call.getOriginNumber(), call.getDestNumber());
        if(callsRepository.existsByOriginNumberAndCallDate(call.getOriginNumber(), call.getCallDate()))
            throw new CallAlreadyExists();
        Calls c = new Calls();
        return callsRepository.save(c.builder()
                .originNumber(call.getOriginNumber())
                .destNumber(call.getDestNumber())
                .duration(call.getDuration())
                .callDate(call.getCallDate()).build());
    }

    public Page<UserCalls> getUserCalls(Pageable pageable, Integer clientId, Integer lineId, Timestamp from, Timestamp to) throws LineNotExists, ClientNotExists {
        linesService.verifyClientAndLine(clientId, lineId);
        Lines line = linesService.findById(lineId);
        Page<UserCalls> p;
        if(from == null || to == null)
            p = callsRepository.findByOriginLine(pageable, line);
        else
            p = callsRepository.findByCallDateBetweenAndOriginLine(pageable, from, to, line);
        return p;
    }

    public List<TopCalls> findTop10Calls(Integer idClient, Integer idLine) throws LineNotExists, ClientNotExists {
        linesService.verifyClientAndLine(idClient, idLine);
        return callsRepository.getFavouritesCalls(idLine);
    }

    public void verifyCallLines(String originNumber, String destNumber) throws LineNotExists, LineNotActive, InvalidPhoneNumber, RateNotExists {
        if(originNumber.length() != 10 ||destNumber.length() != 10)
            throw new InvalidPhoneNumber();
        Lines o = linesService.findByLineNumber(originNumber);
        Lines d = linesService.findByLineNumber(destNumber);
        if(!ratesService.existsByOriginLineAndDestLine(o.getCity(), d.getCity()))
            throw new RateNotExists();
        if(!linesService.existsByStatusAndId(Lines.Status.ACTIVE, o.getId()) || !linesService.existsByStatusAndId(Lines.Status.ACTIVE, d.getId()) )
            throw new LineNotActive();
    }
}