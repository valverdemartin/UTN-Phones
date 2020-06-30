package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Exceptions.*;
import edu.utn.TpFinal.Projections.TopCalls;
import edu.utn.TpFinal.Projections.UserCalls;
import edu.utn.TpFinal.model.Calls;
import edu.utn.TpFinal.model.DTO.CallsDTO;
import edu.utn.TpFinal.model.Lines;
import edu.utn.TpFinal.service.CallsService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CallsControllerTest {
    CallsService callsService;
    CallsController callsController;

    @Before
    public void onSetUp() {
        callsService = mock(CallsService.class);
        callsController = new CallsController(callsService);
    }

    /*@Test
    public void getCallsOk(){
        Pageable pageable = PageRequest.of(0,1);
        Calls mockedCall = Calls.builder()
                .callDate(Timestamp.valueOf(LocalDateTime.now()))
                .destNumber("2235520040")
                .duration(200)
                .originNumber("2235520040").bill(null).id(1)
                .rate(null)
                .totalPrice(2.5).build();
        Page<Calls> callsPage = new PageImpl<>(Collections.singletonList(mockedCall));
        when(callsService.getCalls(pageable)).thenReturn(callsPage);
        callsService.getCalls(pageable);
        verify(callsService, times(1)).getCalls(pageable);
    }

    @Test
    public void getCallsEmpty(){
        Pageable pageable = PageRequest.of(0,1);
        Page<Calls> callsPage = new PageImpl<>(Collections.emptyList());
        when(callsController.getCalls(pageable)).thenReturn(callsPage);
        callsService.getCalls(pageable);
        verify(callsService, times(1)).getCalls(pageable);
    }*/

    @Test
    public void addCallOk() throws LineNotActive, RateNotExists, InvalidPhoneNumber, LineNotExists, CallAlreadyExists {
        Calls mockedCall = Calls.builder().id(1)
                .destLine(Lines.builder().id(1).build())
                .originLine(Lines.builder().id(2).build())
                .callDate(Timestamp.valueOf(LocalDateTime.now()))
                .destNumber("2235520040")
                .duration(200)
                .originNumber("2235520040").bill(null).id(1)
                .rate(null)
                .totalPrice(2.5).build();
        CallsDTO mockedCallDTO = CallsDTO.builder()
                .callDate(Timestamp.valueOf(LocalDateTime.now()))
                .destNumber("2235520040")
                .duration(200)
                .originNumber("2235520040")
                .build();
        when(callsController.addCall(mockedCallDTO)).thenReturn(mockedCall);
        Calls returned = callsController.addCall(mockedCallDTO);
        verify(callsService, times(1)).addCall(mockedCallDTO);
        assertEquals(mockedCall, returned);
    }


    @Test
    public void getUserCallsOk() throws LineNotExists, ClientNotExists {
        Integer clientId = 1;
        Integer lineId = 1;
        Timestamp date = Timestamp.valueOf(LocalDateTime.now());
        Pageable pageable = PageRequest.of(0,1);
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        UserCalls userCalls = factory.createProjection(UserCalls.class);
        userCalls.setCallDate(date);
        userCalls.setDestNumber("2235520040");
        userCalls.setDuration(200);
        userCalls.setTotalPrice(200.5);
        Page<UserCalls> userCallsPage = new PageImpl<>(Collections.singletonList(userCalls));
        when(callsController.getUserCalls(pageable,clientId,lineId,date,date)).thenReturn(userCallsPage);
        Page<UserCalls> pg = callsController.getUserCalls(pageable,clientId,lineId,date,date);
        verify(callsService, times(1)).getUserCalls(pageable,clientId,lineId,date,date);
        assertEquals(userCallsPage, pg);
    }

    @Test
    public void findTop10CallsOk() throws LineNotExists, ClientNotExists {
        Integer clientId = 1;
        Integer lineId = 1;
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        TopCalls topCalls = factory.createProjection(TopCalls.class);
        topCalls.setDestNumber("2235520040");
        topCalls.setCount(20);
        List<TopCalls> list = Arrays.asList(topCalls);
        when(callsController.findTop10Calls(clientId,lineId)).thenReturn(list);
        List<TopCalls> l = callsController.findTop10Calls(clientId,lineId);
        verify(callsService, times(1)).findTop10Calls(clientId,lineId);
        assertEquals(list, l);
    }

}
