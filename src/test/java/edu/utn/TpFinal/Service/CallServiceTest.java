package edu.utn.TpFinal.Service;

import edu.utn.TpFinal.Exceptions.*;
import edu.utn.TpFinal.Projections.TopCalls;
import edu.utn.TpFinal.Projections.UserCalls;
import edu.utn.TpFinal.model.Calls;
import edu.utn.TpFinal.model.Cities;
import edu.utn.TpFinal.model.Clients;
import edu.utn.TpFinal.model.DTO.CallsDTO;
import edu.utn.TpFinal.model.Lines;
import edu.utn.TpFinal.repository.CallsRepository;
import edu.utn.TpFinal.repository.ClientsRepository;
import edu.utn.TpFinal.repository.LinesRespository;
import edu.utn.TpFinal.service.CallsService;
import edu.utn.TpFinal.service.LinesService;
import edu.utn.TpFinal.service.RatesService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import javax.sound.midi.MidiChannel;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CallServiceTest {

    private CallsRepository callsRepository;
    private CallsService callsService;
    private LinesService linesService;
    private RatesService ratesService;
    private ClientsRepository clientsRepository;
    private LinesRespository linesRespository;

    @Before
    public void onSetUp(){
        callsRepository= mock(CallsRepository.class);
        linesService = mock(LinesService.class);
        ratesService = mock(RatesService.class);
        clientsRepository = mock(ClientsRepository.class);
        linesRespository = mock(LinesRespository.class);
        callsService = new CallsService(callsRepository, linesService, ratesService);
    }

    @Test
    public void addCall() throws LineNotActive, RateNotExists, InvalidPhoneNumber, LineNotExists, CallAlreadyExists {
        Lines line = Lines.builder().city(Cities.builder().id(1).build())
                .client(Clients.builder().id(1).build())
                .phoneNumber("2235520040")
                .status(Lines.Status.CANCELLED)
                .type(Lines.Type.MOBILE)
                .id(1).build();
        CallsDTO callsDTO = CallsDTO.builder()
                .callDate(Timestamp.valueOf(LocalDateTime.now()))
                .destNumber("2235520040")
                .duration(200)
                .originNumber("2234450060")
                .build();
        Calls mockedCall = Calls.builder().id(1)
                .destLine(line)
                .originLine(Lines.builder().id(2).build())
                .callDate(Timestamp.valueOf(LocalDateTime.now()))
                .destNumber("2235520040")
                .duration(200)
                .originNumber("2235520040").bill(null).id(1)
                .rate(null)
                .totalPrice(2.5).build();
        when(linesService.findByLineNumber(callsDTO.getOriginNumber())).thenReturn(line);
        when(linesService.findByLineNumber(callsDTO.getDestNumber())).thenReturn(line);
        when(ratesService.existsByOriginLineAndDestLine(line.getCity(), line.getCity())).thenReturn(true);
        when(linesService.existsByStatusAndId(Lines.Status.ACTIVE, line.getId())).thenReturn(true);
        when(callsRepository.existsByOriginNumberAndCallDate(callsDTO.getOriginNumber(), callsDTO.getCallDate())).thenReturn(false);
        when(callsRepository.save(any(Calls.class))).thenReturn(mockedCall);
        Calls createdCall = callsService.addCall(callsDTO);
        assertEquals(mockedCall, createdCall);
        verify(callsRepository, times(1)).save(any(Calls.class));
    }

    @Test(expected = InvalidPhoneNumber.class)
    public void addCallsIncorrectOrigNumber() throws LineNotActive, RateNotExists, InvalidPhoneNumber, LineNotExists, CallAlreadyExists {
        CallsDTO callsDTO = CallsDTO.builder()
                .callDate(Timestamp.valueOf(LocalDateTime.now()))
                .destNumber("1")
                .duration(200)
                .originNumber("2235520040")
                .build();
        Calls createdCall = callsService.addCall(callsDTO);
    }

    @Test(expected = InvalidPhoneNumber.class)
    public void addCallsIncorrectDestNumber() throws LineNotActive, RateNotExists, InvalidPhoneNumber, LineNotExists, CallAlreadyExists {
        CallsDTO callsDTO = CallsDTO.builder()
                .callDate(Timestamp.valueOf(LocalDateTime.now()))
                .destNumber("2235520040")
                .duration(200)
                .originNumber("1")
                .build();
        Calls createdCall = callsService.addCall(callsDTO);
    }

    @Test(expected = LineNotExists.class)
    public void addCallsIncorrectNumbers() throws LineNotExists, LineNotActive, RateNotExists, InvalidPhoneNumber, CallAlreadyExists {
        CallsDTO callsDTO = CallsDTO.builder()
                .callDate(Timestamp.valueOf(LocalDateTime.now()))
                .destNumber("2235520040")
                .duration(200)
                .originNumber("2235520040")
                .build();
        when(linesService.findByLineNumber(callsDTO.getOriginNumber())).thenThrow(new LineNotExists());
        Calls createdCall = callsService.addCall(callsDTO);
    }

    @Test(expected = RateNotExists.class)
    public void addCallsRateNotExists() throws LineNotExists, LineNotActive, RateNotExists, InvalidPhoneNumber, CallAlreadyExists {
        Lines line = Lines.builder().city(Cities.builder().id(1).build())
                .client(Clients.builder().id(1).build())
                .phoneNumber("2235520040")
                .status(Lines.Status.CANCELLED)
                .type(Lines.Type.MOBILE)
                .id(1).build();
        CallsDTO callsDTO = CallsDTO.builder()
                .callDate(Timestamp.valueOf(LocalDateTime.now()))
                .destNumber("2235520040")
                .duration(200)
                .originNumber("2235520040")
                .build();
        when(linesService.findByLineNumber(callsDTO.getOriginNumber())).thenReturn(line);
        when(linesService.findByLineNumber(callsDTO.getDestNumber())).thenReturn(line);
        when(ratesService.existsByOriginLineAndDestLine(line.getCity(), line.getCity())).thenReturn(false);
        Calls createdCall = callsService.addCall(callsDTO);
    }

    @Test(expected = LineNotActive.class)
    public void addCallsLineOrigNotActive() throws LineNotExists, LineNotActive, RateNotExists, InvalidPhoneNumber, CallAlreadyExists {
        Lines lineO = Lines.builder().city(Cities.builder().id(1).build())
                .client(Clients.builder().id(1).build())
                .phoneNumber("2235520040")
                .status(Lines.Status.CANCELLED)
                .type(Lines.Type.MOBILE)
                .id(2).build();
        Lines lineD = Lines.builder().city(Cities.builder().id(1).build())
                .client(Clients.builder().id(1).build())
                .phoneNumber("2235520041")
                .status(Lines.Status.ACTIVE)
                .type(Lines.Type.MOBILE)
                .id(1).build();
        CallsDTO callsDTO = CallsDTO.builder()
                .callDate(Timestamp.valueOf(LocalDateTime.now()))
                .destNumber("2235520040")
                .duration(200)
                .originNumber("2235520041")
                .build();
        when(linesService.findByLineNumber(callsDTO.getOriginNumber())).thenReturn(lineO);
        when(linesService.findByLineNumber(callsDTO.getDestNumber())).thenReturn(lineD);
        when(ratesService.existsByOriginLineAndDestLine(lineO.getCity(), lineD.getCity())).thenReturn(true);
        when(linesService.existsByStatusAndId(Lines.Status.ACTIVE, lineO.getId())).thenReturn(false);
        when(linesService.existsByStatusAndId(Lines.Status.ACTIVE, lineD.getId())).thenReturn(true);
        Calls createdCall = callsService.addCall(callsDTO);
    }

    @Test(expected = LineNotActive.class)
    public void addCallsLineDestNotActive() throws LineNotExists, LineNotActive, RateNotExists, InvalidPhoneNumber, CallAlreadyExists {
        Lines lineO = Lines.builder().city(Cities.builder().id(1).build())
                .client(Clients.builder().id(1).build())
                .phoneNumber("2235520040")
                .status(Lines.Status.CANCELLED)
                .type(Lines.Type.MOBILE)
                .id(2).build();
        Lines lineD = Lines.builder().city(Cities.builder().id(1).build())
                .client(Clients.builder().id(1).build())
                .phoneNumber("2235520041")
                .status(Lines.Status.ACTIVE)
                .type(Lines.Type.MOBILE)
                .id(1).build();
        CallsDTO callsDTO = CallsDTO.builder()
                .callDate(Timestamp.valueOf(LocalDateTime.now()))
                .destNumber("2235520040")
                .duration(200)
                .originNumber("2235520041")
                .build();
        when(linesService.findByLineNumber(callsDTO.getOriginNumber())).thenReturn(lineO);
        when(linesService.findByLineNumber(callsDTO.getDestNumber())).thenReturn(lineD);
        when(ratesService.existsByOriginLineAndDestLine(lineO.getCity(), lineD.getCity())).thenReturn(true);
        when(linesService.existsByStatusAndId(Lines.Status.ACTIVE, lineO.getId())).thenReturn(true);
        when(linesService.existsByStatusAndId(Lines.Status.ACTIVE, lineD.getId())).thenReturn(false);
        Calls createdCall = callsService.addCall(callsDTO);
    }

    @Test(expected = CallAlreadyExists.class)
    public void addCallAlreadyExists() throws LineNotExists, LineNotActive, RateNotExists, InvalidPhoneNumber, CallAlreadyExists {
        Lines line = Lines.builder().city(Cities.builder().id(1).build())
                .client(Clients.builder().id(1).build())
                .phoneNumber("2235520040")
                .status(Lines.Status.CANCELLED)
                .type(Lines.Type.MOBILE)
                .id(1).build();
        CallsDTO callsDTO = CallsDTO.builder()
                .callDate(Timestamp.valueOf(LocalDateTime.now()))
                .destNumber("2235520040")
                .duration(200)
                .originNumber("2235520040")
                .build();
        when(linesService.findByLineNumber(callsDTO.getOriginNumber())).thenReturn(line);
        when(linesService.findByLineNumber(callsDTO.getDestNumber())).thenReturn(line);
        when(ratesService.existsByOriginLineAndDestLine(line.getCity(), line.getCity())).thenReturn(true);
        when(linesService.existsByStatusAndId(Lines.Status.ACTIVE, line.getId())).thenReturn(true);
        when(callsRepository.existsByOriginNumberAndCallDate(callsDTO.getOriginNumber(), callsDTO.getCallDate())).thenReturn(true);
        Calls createdCall = callsService.addCall(callsDTO);
    }

    @Test
    public void getUserCalls() throws LineNotExists, ClientNotExists {
        Lines line = Lines.builder().city(Cities.builder().id(1).build())
                .client(Clients.builder().id(1).build())
                .phoneNumber("2235520040")
                .status(Lines.Status.CANCELLED)
                .type(Lines.Type.MOBILE)
                .id(1).build();
        Clients c = Clients.builder().id(1).build();
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
        when(clientsRepository.findById(clientId)).thenReturn(Optional.ofNullable(c));
        when(linesRespository.existsByIdAndClient(lineId, c)).thenReturn(true);
        when(linesService.findById(lineId)).thenReturn(line);
        when(callsRepository.findByOriginLine(pageable,line)).thenReturn(userCallsPage);
        when(callsService.getUserCalls(pageable,clientId, lineId, date, date)).thenReturn(userCallsPage);
        Page<UserCalls> pageReturned = callsService.getUserCalls(pageable,clientId, lineId, date, date);
        assertEquals(userCallsPage, pageReturned);
    }

    @Test
    public void getUserCallsFromNull() throws LineNotExists, ClientNotExists {
        Lines line = Lines.builder().city(Cities.builder().id(1).build())
                .client(Clients.builder().id(1).build())
                .phoneNumber("2235520040")
                .status(Lines.Status.CANCELLED)
                .type(Lines.Type.MOBILE)
                .id(1).build();
        Clients c = Clients.builder().id(1).build();
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
        when(clientsRepository.findById(clientId)).thenReturn(Optional.ofNullable(c));
        when(linesRespository.existsByIdAndClient(lineId, c)).thenReturn(true);
        when(linesService.findById(lineId)).thenReturn(line);
        when(callsRepository.findByOriginLine(pageable,line)).thenReturn(userCallsPage);
        when(callsService.getUserCalls(pageable,clientId, lineId, null, date)).thenReturn(userCallsPage);
        Page<UserCalls> pageReturned = callsService.getUserCalls(pageable,clientId, lineId, null, date);
        assertEquals(userCallsPage, pageReturned);
    }

    @Test
    public void getUserCallsToNull() throws LineNotExists, ClientNotExists {
        Lines line = Lines.builder().city(Cities.builder().id(1).build())
                .client(Clients.builder().id(1).build())
                .phoneNumber("2235520040")
                .status(Lines.Status.CANCELLED)
                .type(Lines.Type.MOBILE)
                .id(1).build();
        Clients c = Clients.builder().id(1).build();
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
        when(clientsRepository.findById(clientId)).thenReturn(Optional.ofNullable(c));
        when(linesRespository.existsByIdAndClient(lineId, c)).thenReturn(true);
        when(linesService.findById(lineId)).thenReturn(line);
        when(callsRepository.findByOriginLine(pageable,line)).thenReturn(userCallsPage);
        when(callsService.getUserCalls(pageable,clientId, lineId, date, null)).thenReturn(userCallsPage);
        Page<UserCalls> pageReturned = callsService.getUserCalls(pageable,clientId, lineId, date, null);
        assertEquals(userCallsPage, pageReturned);
    }

    @Test
    public void getUserCallsFromAnToNulls() throws LineNotExists, ClientNotExists {
        Lines line = Lines.builder().city(Cities.builder().id(1).build())
                .client(Clients.builder().id(1).build())
                .phoneNumber("2235520040")
                .status(Lines.Status.CANCELLED)
                .type(Lines.Type.MOBILE)
                .id(1).build();
        Clients c = Clients.builder().id(1).build();
        Integer clientId = 1;
        Integer lineId = 1;
        Pageable pageable = PageRequest.of(0,1);
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        UserCalls userCalls = factory.createProjection(UserCalls.class);
        userCalls.setCallDate(null);
        userCalls.setDestNumber("2235520040");
        userCalls.setDuration(200);
        userCalls.setTotalPrice(200.5);
        Page<UserCalls> userCallsPage = new PageImpl<>(Collections.singletonList(userCalls));
        when(clientsRepository.findById(clientId)).thenReturn(Optional.ofNullable(c));
        when(linesRespository.existsByIdAndClient(lineId, c)).thenReturn(true);
        when(linesService.findById(lineId)).thenReturn(line);
        when(callsRepository.findByOriginLine(pageable,line)).thenReturn(userCallsPage);
        when(callsService.getUserCalls(pageable,clientId, lineId, null, null)).thenReturn(userCallsPage);
        Page<UserCalls> pageReturned = callsService.getUserCalls(pageable,clientId, lineId, null, null);
        assertEquals(userCallsPage, pageReturned);
    }



    @Test(expected =  ClientNotExists.class)
    public void getUserCallsClientNotExists() throws LineNotExists, ClientNotExists {
        Lines line = Lines.builder().city(Cities.builder().id(1).build())
                .client(Clients.builder().id(1).build())
                .phoneNumber("2235520040")
                .status(Lines.Status.CANCELLED)
                .type(Lines.Type.MOBILE)
                .id(1).build();
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
        doThrow(ClientNotExists.class).when(linesService).verifyClientAndLine(clientId,lineId);
        Page<UserCalls> pageReturned = callsService.getUserCalls(pageable,clientId, lineId, date, date);
    }

    @Test
    public void findTop10Calls() throws LineNotExists, ClientNotExists {
        Integer clientId = 1;
        Integer lineId = 1;
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        TopCalls topCalls = factory.createProjection(TopCalls.class);
        topCalls.setCount(20);
        topCalls.setDestNumber("2235520040");
        List<TopCalls> list = Arrays.asList(topCalls, topCalls);
        when(callsService.findTop10Calls(clientId, lineId)).thenReturn(list);
        List<TopCalls> L2 = callsRepository.getFavouritesCalls(lineId);
        assertEquals(list, L2);
    }

}

