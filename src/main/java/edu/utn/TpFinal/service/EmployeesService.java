package edu.utn.TpFinal.service;

import edu.utn.TpFinal.model.Employees;
import edu.utn.TpFinal.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class EmployeesService {
    private final EmployeesRepository employeesRepository;

    @Autowired
    public EmployeesService(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    public Employees getEmployeeById(Integer employeeId){
        return employeesRepository.findById(employeeId).get();
    }

    public List<Employees> getEmployees(){
        return employeesRepository.findAll();
    }

    public void addEmployee(final Employees employee){
        employeesRepository.save(employee);
    }
}
