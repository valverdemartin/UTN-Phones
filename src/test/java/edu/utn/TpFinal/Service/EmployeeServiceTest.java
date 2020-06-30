package edu.utn.TpFinal.Service;

import edu.utn.TpFinal.model.Employees;
import edu.utn.TpFinal.repository.EmployeesRepository;
import edu.utn.TpFinal.service.EmployeesService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {

    private EmployeesRepository employeesRepository;
    private EmployeesService employeesService;

    @Before
    public void onSetUp() {
        employeesRepository = mock(EmployeesRepository.class);
        employeesService = new EmployeesService(employeesRepository);
    }

    @Test
    public void loginOk(){
        Employees e = Employees.builder().active(true).dni(39137741)
                .lastName("Valverde")
                .password("123123")
                .userName("Tincho09")
                .id(1)
                .name("Martin")
                .build();
        when(employeesRepository.findByUserNameAndPassword(e.getUserName(), e.getPassword())).thenReturn(e);
        Employees e2 = employeesRepository.findByUserNameAndPassword(e.getUserName(), e.getPassword());
        assertEquals(e,e2);
    }

}
