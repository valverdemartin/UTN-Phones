package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Exceptions.UserNotExists;
import edu.utn.TpFinal.model.Clients;
import edu.utn.TpFinal.model.DTO.LoginRequestDto;
import edu.utn.TpFinal.model.Employees;
import edu.utn.TpFinal.service.CallsService;
import edu.utn.TpFinal.service.EmployeesService;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ValidationException;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class EmployeeControllerTest {

    private EmployeesController employeesController;
    private EmployeesService employeesService;

    @Before
    public void onSetUp() {
        employeesService = mock(EmployeesService.class);
        employeesController = new EmployeesController(employeesService);
    }

    @Test
    public void employeeLogin(){
        Employees loggedEmployee = Employees.builder().name("test").lastName("Valverde").dni(39137741).active(true)
                .password("123").userName("test").build();
        LoginRequestDto testLogin = new LoginRequestDto();
        testLogin.setPassword("123");
        testLogin.setUsername("test");
        doReturn(loggedEmployee).when(employeesService).login(testLogin.getUsername(), testLogin.getPassword());
        employeesController.login(loggedEmployee.getUserName(), loggedEmployee.getPassword());
        verify(employeesService, times(1)).login(testLogin.getUsername(), testLogin.getPassword());
    }


    @Test(expected = ValidationException.class)
    public void employeeEmptyUsernamePassword() throws ValidationException{
        LoginRequestDto testLogin = new LoginRequestDto();
        testLogin.setUsername(null);
        testLogin.setPassword(null);
        when(employeesController.login(testLogin.getUsername(), testLogin.getPassword())).thenThrow(new ValidationException());
        employeesController.login(testLogin.getUsername(), testLogin.getPassword());
    }

    @Test(expected = ValidationException.class)
    public void employeeEmptyPassword() throws ValidationException{
        LoginRequestDto testLogin = new LoginRequestDto();
        testLogin.setUsername("null");
        testLogin.setPassword(null);
        when(employeesController.login(testLogin.getUsername(), testLogin.getPassword())).thenThrow(new ValidationException());
        employeesController.login(testLogin.getUsername(), testLogin.getPassword());
    }

}
