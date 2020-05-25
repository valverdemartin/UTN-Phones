package edu.utn.TpFinal.repository;

import edu.utn.TpFinal.model.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository

public interface ClientsRepository extends JpaRepository<Clients, Integer> {
    Clients findByLastName(String lastName);
    @Query(value = "call sp_active_user (?1)", nativeQuery = true)
    void activeUser(Integer clientId);
}
