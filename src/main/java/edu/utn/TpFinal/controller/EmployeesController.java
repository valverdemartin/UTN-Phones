package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.model.Employees;
import edu.utn.TpFinal.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.validation.ValidationException;


@Controller
public class EmployeesController {
    private final EmployeesService employeesService;

    @Autowired
    public EmployeesController(final EmployeesService employeesService) {
        this.employeesService = employeesService;
    }
    public Employees login(String username, String password) throws ValidationException {
        if ((username != null) && (password != null)) {
            return employeesService.login(username, password);
        } else {
            throw new ValidationException("username and password must have a value");
        }
    }

    /*//@GetMapping("/{employeeId}")
    public ResponseEntity<Employees>  getEmployeeById(@PathVariable Integer employeeId) throws UserNotExists {
        return ResponseEntity.ok(employeesService.getEmployeeById(employeeId));
    }

    //@GetMapping("/")
    public Page<Employees> getEmployees(@PageableDefault(page=0, size=5) Pageable pageable){
        return employeesService.getEmployees(pageable);
    }


    //@PostMapping("/")
    public void addEmployee(@RequestBody @Valid final Employees employee){
        employeesService.addEmployee(employee);
    }

    //@PutMapping("/")
    public ResponseEntity<Employees> updateEmployee(@RequestBody @Valid final Employees employee, @RequestParam(value="active", required = false) boolean active) throws UserNotExists, UserDniAlreadyExist, UserNameAlreadyExist, UserAlreadyDeleted, UserAlreadyActive, DeletionNotAllowed {
        return ResponseEntity.ok(employeesService.updateEmployee(employee, active));
    }

    //@DeleteMapping("/{employeeId}/")
    public ResponseEntity<Employees> deleteEmployee(@PathVariable @Valid final Integer employeeId) throws UserNotExists, UserAlreadyDeleted, UserAlreadyActive{
        return ResponseEntity.ok(employeesService.deleteEmployee(employeeId));
    }*/


}
