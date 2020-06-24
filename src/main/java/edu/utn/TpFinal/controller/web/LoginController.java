package edu.utn.TpFinal.controller.web;

import edu.utn.TpFinal.Exceptions.InvalidLoginException;
import edu.utn.TpFinal.Exceptions.UserNotExists;
import edu.utn.TpFinal.controller.ClientsController;
import edu.utn.TpFinal.controller.EmployeesController;
import edu.utn.TpFinal.dto.LoginRequestDto;
import edu.utn.TpFinal.model.Clients;
import edu.utn.TpFinal.model.Employees;
import edu.utn.TpFinal.model.UserLogin;
import edu.utn.TpFinal.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;

@RestController
@RequestMapping("/")
public class LoginController {

    EmployeesController employessController;
    ClientsController clientsController;
    SessionManager sessionManager;

    @Autowired
    public LoginController(ClientsController clientsController, EmployeesController employessController,SessionManager sessionManager) {
        this.employessController = employessController;
        this.clientsController = clientsController;
        this.sessionManager = sessionManager;
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto loginRequestDto) throws ValidationException, InvalidLoginException {
        ResponseEntity response;
        try {
            UserLogin loggedUser = validateLogin((loginRequestDto));
            String token = sessionManager.createSession(loggedUser);
            response = ResponseEntity.ok().headers(createHeaders(token)).build();
        } catch (UserNotExists e) {
            throw new InvalidLoginException();
        }
        return response;
    }


    @PostMapping("/logout")
    public ResponseEntity logout(@RequestHeader("Authorization") String token) {
        sessionManager.removeSession(token);
        return ResponseEntity.ok().build();
    }

    private HttpHeaders createHeaders(String token) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", token);
        return responseHeaders;
    }

    private UserLogin validateLogin (LoginRequestDto loginRequestDto) throws  UserNotExists {
        UserLogin loggedUser = new UserLogin();
        Clients client = clientsController.login(loginRequestDto.getUsername(), loginRequestDto.getPassword());
        if (client != null) {
            loggedUser.setId(client.getId());
            loggedUser.setUserType(client.getClass().getSimpleName());

        } else {
            Employees employee = employessController.login(loginRequestDto.getUsername(), loginRequestDto.getPassword());
            if (employee != null) {
                loggedUser.setId(employee.getId());
                loggedUser.setUserType(employee.getClass().getSimpleName());
            } else {
                throw new UserNotExists();
            }
        }
        return loggedUser;
    }

}