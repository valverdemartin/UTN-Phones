package edu.utn.TpFinal.repository;

import edu.utn.TpFinal.model.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface EmployeesRepository extends JpaRepository<Employees, Integer> {
    Employees findByUserNameAndPassword(String username, String password);
    //boolean existsByIdNotAndUserName(Integer id, String userName);
    //boolean existsByIdNotAndDni(Integer id, Integer dni);
    //Optional<Employees>  findByIdAndActiveTrue(Integer employeeId);
}
