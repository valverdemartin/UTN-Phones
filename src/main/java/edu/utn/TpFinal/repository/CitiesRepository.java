package edu.utn.TpFinal.repository;

import edu.utn.TpFinal.model.Cities;
import edu.utn.TpFinal.model.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CitiesRepository extends JpaRepository<Cities, Integer> {
}
