package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Exceptions.*;
import edu.utn.TpFinal.model.Employees;
import edu.utn.TpFinal.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;


@RestController
@RequestMapping("/employee")
public class EmployeesController {
    private final EmployeesService employeesService;

    @Autowired
    public EmployeesController(final EmployeesService employeesService) {
        this.employeesService = employeesService;
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employees>  getEmployeeById(@PathVariable Integer employeeId) throws UserNotExists {
        return ResponseEntity.ok(employeesService.getEmployeeById(employeeId));
    }

    @GetMapping("/")
    public Page<Employees> getEmployees(@PageableDefault(page=0, size=5) Pageable pageable){
        return employeesService.getEmployees(pageable);
    }


    @PostMapping("/")
    public void addEmployee(@RequestBody @Valid final Employees employee){
        employeesService.addEmployee(employee);
    }

    @PutMapping("/{active}/")
    public ResponseEntity<Employees> updateEmployee(@RequestBody @Valid final Employees employee, @PathVariable boolean active) throws UserNotExists, UserDniAlreadyExist, UserNameAlreadyExist, UserAlreadyDeleted, UserAlreadyActive, DeletionNotAllowed {
        return ResponseEntity.ok(employeesService.updateEmployee(employee, active));
    }

    @DeleteMapping("/{employeeId}/")
    public ResponseEntity<Employees> deleteEmployee(@PathVariable @Valid final Integer employeeId) throws UserNotExists, UserAlreadyDeleted, UserAlreadyActive{
        return ResponseEntity.ok(employeesService.deleteEmployee(employeeId));
    }

    public Employees login(String username, String password) throws ValidationException {
        if ((username != null) && (password != null)) {
            return employeesService.login(username, password);
        } else {
            throw new ValidationException("username and password must have a value");
        }
    }
}
