package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Projections.LastCall;
import edu.utn.TpFinal.service.CallsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CallsControllerTest {

    private CallsService callsService;
    private CallsController callsController;
    @Before
    public void onSetUp(){
        callsService= mock(CallsService.class);
        callsController = new CallsController(callsService);
    }

    @Test
    public void getLastCallsOkNull(){
        when(callsService.getLastsCalls()).thenReturn(Collections.emptyList());
        ResponseEntity<List<LastCall>> rp = callsController.getLastsCalls();
        Assert.assertEquals(HttpStatus.NO_CONTENT, rp.getStatusCode());
    }
}
