package edu.utn.TpFinal.Service;

import edu.utn.TpFinal.Projections.LastCall;
import edu.utn.TpFinal.repository.CallsRepository;
import edu.utn.TpFinal.service.CallsService;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CallsServiceTest {

    CallsRepository callsRepository;
    CallsService callsService;

    @Before
    public void onSetUp(){
        callsRepository= mock(CallsRepository.class);
        callsService = new CallsService(callsRepository);
    }

    @Test
    public void getLastsCalls(){
        when(callsRepository.getLastCalls()).thenReturn(Collections.emptyList());
        callsService.getLastsCalls();
    }
}
