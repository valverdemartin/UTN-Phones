package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Exceptions.*;
import edu.utn.TpFinal.Projections.TopCalls;
import edu.utn.TpFinal.Projections.UserCalls;
import edu.utn.TpFinal.model.Calls;
import edu.utn.TpFinal.model.DTO.CallsDTO;
import edu.utn.TpFinal.service.CallsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;
import java.util.List;

@Controller
public class CallsController {
    private CallsService callsService;

    @Autowired
    public CallsController(CallsService CallsService) {
        this.callsService = CallsService;
    }

    public Calls addCall(CallsDTO call) throws CallAlreadyExists, LineNotExists, LineNotActive, InvalidPhoneNumber, RateNotExists {
        return callsService.addCall(call);
    }

    public Page<UserCalls> getUserCalls(Pageable pageable, Integer clientId, Integer lineId,
                                        Timestamp from, Timestamp to) throws LineNotExists, ClientNotExists {
        return callsService.getUserCalls(pageable, clientId, lineId, from, to);
    }


    public List<TopCalls> findTop10Calls(Integer clientId, Integer lineId) throws LineNotExists, ClientNotExists {
        return callsService.findTop10Calls(clientId, lineId);
    }








}