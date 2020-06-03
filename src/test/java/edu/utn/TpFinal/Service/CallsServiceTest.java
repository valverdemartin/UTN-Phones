package edu.utn.TpFinal.Service;

import edu.utn.TpFinal.Projections.LastCall;
import edu.utn.TpFinal.repository.CallsRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CallsServiceTest {

    CallsRepository callsRepository;

    @Before
    public void onSetUp(){
        callsRepository= mock(CallsRepository.class);
    }

    @Test
    public void getLastsCalls(){
        List<LastCall>lc = null;
        when(callsRepository.getLastCalls()).thenReturn(lc);
        callsRepository.getLastCalls();
    }
}
