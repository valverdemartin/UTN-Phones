package edu.utn.TpFinal.service;

import edu.utn.TpFinal.Exceptions.*;
import edu.utn.TpFinal.model.Employees;
import edu.utn.TpFinal.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class EmployeesService {
    private final EmployeesRepository employeesRepository;

    @Autowired
    public EmployeesService(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    public Employees getEmployeeById(Integer employeeId) throws UserNotExists {
        return employeesRepository.findByIdAndActiveTrue(employeeId).orElseThrow(()-> new UserNotExists());
    }

    public Page<Employees> getEmployees(Pageable pageable){
        return employeesRepository.findAll(pageable);
    }

    public void addEmployee(final Employees employee){
        employeesRepository.save(employee);
    }

    public Employees updateEmployee(Employees employee, Boolean active) throws UserNotExists, UserDniAlreadyExist, UserNameAlreadyExist, UserAlreadyDeleted, UserAlreadyActive, DeletionNotAllowed {
        if(!employeesRepository.existsById(employee.getId()))
            throw new UserNotExists();
        if(employee.getActive()){
            if(active)
                activeValidationClient(employee.getId(), true);
        }else{
            throw new DeletionNotAllowed();
        }
        dniAndUserNameValidations(employee);
        return employeesRepository.save(employee);
    }

    public Employees deleteEmployee(Integer id) throws UserAlreadyDeleted, UserAlreadyActive, UserNotExists{
        Employees employee = employeesRepository.findById(id).orElseThrow(()-> new UserNotExists());
        activeValidationClient(id, false);
        employee.setActive(false);
        return employeesRepository.save(employee);
    }

    public Employees activeEmployee(Employees employee) throws UserAlreadyDeleted, UserAlreadyActive, UserNotExists {
        activeValidationClient(employee.getId(), true);
        return employeesRepository.save(employee);
    }

    ///////////////////////////VALIDATIONS//////////////////////////////////////////
    public void activeValidationClient(Integer id, Boolean active) throws UserAlreadyDeleted, UserAlreadyActive, UserNotExists {
        Employees employee= employeesRepository.findById(id).orElseThrow(()-> new UserNotExists());
        if(!active && !employee.getActive())
            throw new UserAlreadyDeleted();
        if(active && employee.getActive())
            throw new UserAlreadyActive();
    }

    public void dniAndUserNameValidations(Employees employee) throws UserDniAlreadyExist, UserNameAlreadyExist {
        if(employeesRepository.existsByIdNotAndDni(employee.getId(), employee.getDni()))
            throw new UserDniAlreadyExist();
        if(employeesRepository.existsByIdNotAndUserName(employee.getId(), employee.getUserName()))
            throw new UserNameAlreadyExist();
    }

    public Employees login(String username, String password) {
        Employees user = employeesRepository.findByUserNameAndPassword(username, password);

        return user;
    }
    ///////////////////////////END VALIDATIONS//////////////////////////////////
}
