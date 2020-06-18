package edu.utn.TpFinal.repository;

import edu.utn.TpFinal.model.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface EmployeesRepository extends JpaRepository<Employees, Integer> {

    boolean existsByIdNotAndUserName(Integer id, String userName);

    boolean existsByIdNotAndDni(Integer id, Integer dni);

    Optional<Employees>  findByIdAndActiveTrue(Integer employeeId);
}
