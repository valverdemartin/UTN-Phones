package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Exceptions.*;
import edu.utn.TpFinal.Projections.UserLine;
import edu.utn.TpFinal.model.Cities;
import edu.utn.TpFinal.model.Clients;
import edu.utn.TpFinal.model.DTO.LineDTO;
import edu.utn.TpFinal.model.Lines;
import edu.utn.TpFinal.service.ClientsService;
import edu.utn.TpFinal.service.LinesService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class LinesControllerTest {

    private LinesController linesController;
    private LinesService linesService;
    private ClientsService clientsService;

    @Before
    public void onSetUp() {
        linesService = mock(LinesService.class);
        clientsService = mock(ClientsService.class);
        linesController = new LinesController(linesService, clientsService);
    }

    @Test
    public void getLineByClientOk() throws LineNotExists, ClientNotExists {
        Integer clientId = 1;
        Integer lineId = 1;
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        UserLine userLine = factory.createProjection(UserLine.class);
        userLine.setPhoneNumber("2235520040");
        userLine.setType("MOBILE");
        when(linesController.getLineByClient(clientId, lineId)).thenReturn(userLine);
        linesController.getLineByClient(clientId, lineId);
        verify(linesService, times(1)).getLineByClient(clientId,lineId);
    }

    @Test
    public void addLine() throws ClientNotExists, InvalidStatus, InvalidPhoneNumber, InvalidPrefix, InvalidType, CityNotExists {
        Integer clientId = 1;
        Lines addLine = Lines.builder()
                .city(Cities.builder().id(1).build())
                .client(Clients.builder().id(1).build())
                .phoneNumber("2235520040")
                .status(Lines.Status.ACTIVE)
                .type(Lines.Type.MOBILE).build();
        Lines createdLine = Lines.builder()
                .city(Cities.builder().id(1).build())
                .client(Clients.builder().id(1).build())
                .phoneNumber("2235520040")
                .status(Lines.Status.ACTIVE)
                .type(Lines.Type.MOBILE)
                .id(1).build();
        when(linesController.addLine(addLine,clientId)).thenReturn(createdLine);
        Lines returnedLine =linesController.addLine(addLine,clientId);
        verify(linesService, times(1)).addLine(addLine,clientId);
        assertEquals(createdLine, returnedLine);
    }

    @Test
    public void updateLine() throws LineNotExists, ClientNotExists, CityNotExists, InvalidStatus, InvalidPhoneNumber, InvalidType, DeletionNotAllowed, InvalidPrefix {
        Integer lineId = 1;
        LineDTO lineDTO = LineDTO.builder()
                .city(Cities.builder()
                        .id(1).build())
                .phoneNumber("2235520040")
                .status(Lines.Status.ACTIVE)
                .type(Lines.Type.MOBILE).build();
        Lines updatedLine = Lines.builder()
                .city(Cities.builder().id(1).build())
                .client(Clients.builder().id(1).build())
                .phoneNumber("2235520040")
                .status(Lines.Status.ACTIVE)
                .type(Lines.Type.MOBILE)
                .id(1).build();
        when(linesController.updateLine(lineDTO,lineId)).thenReturn(updatedLine);
        Lines returned = linesController.updateLine(lineDTO,lineId);
        verify(linesService, times(1)).updateLine(lineDTO,lineId);
        assertEquals(updatedLine, returned);
    }

    @Test
    public void deleteLine() throws LineNotExists, ClientNotExists, LineAlreadyDeleted {
        Integer lineId = 1;
        Lines deletedLine = Lines.builder()
                .city(Cities.builder().id(1).build())
                .client(Clients.builder().id(1).build())
                .phoneNumber("2235520040")
                .status(Lines.Status.CANCELLED)
                .type(Lines.Type.MOBILE)
                .id(1).build();
        when(linesController.deleteLine(lineId)).thenReturn(deletedLine);
        Lines returned = linesController.deleteLine(lineId);
        verify(linesService, times(1)).deleteLine(lineId);
        assertEquals(deletedLine, returned);
    }

    @Test
    public void getClientsLines() throws ClientNotExists {
        Integer clientId = 1;
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        UserLine userLine = factory.createProjection(UserLine.class);
        userLine.setType("MOBILE");
        userLine.setPhoneNumber("2235520040");
        Pageable pageable = PageRequest.of(0,1);
        Page<UserLine> callsPage = new PageImpl<>(Collections.singletonList(userLine));
        when(linesController.getClientsLines(pageable,clientId)).thenReturn(callsPage);
        Page<UserLine> returned = linesController.getClientsLines(pageable,clientId);
        assertEquals(callsPage,returned);
    }

}
