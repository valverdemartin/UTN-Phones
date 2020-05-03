package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.model.Employees;
import edu.utn.TpFinal.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeesController {
    private final EmployeesService employeesService;

    @Autowired
    public EmployeesController(final EmployeesService employeesService) {
        this.employeesService = employeesService;
    }

    @GetMapping("/{employeeId}")
    public Employees getEmployeeById(@RequestParam Integer employeeId){
        return employeesService.getEmployeeById(employeeId);
    }

    @GetMapping("/all/")
    public List<Employees> getEmployees(){
        return employeesService.getEmployees();
    }

    @PostMapping("/")
    public void addEmployee(@RequestBody @Valid final Employees employee){
        employeesService.addEmployee(employee);
    }
}
