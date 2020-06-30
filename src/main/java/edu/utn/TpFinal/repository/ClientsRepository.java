package edu.utn.TpFinal.repository;

import edu.utn.TpFinal.model.Clients;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface ClientsRepository extends JpaRepository<Clients, Integer> {

    boolean existsByDni(Integer dni);

    boolean existsByUserName(String userName);

    boolean existsByIdNotAndDni(Integer id, Integer dni);

    boolean existsByIdNotAndUserName(Integer id, String userName);

    Optional<Clients> findByIdAndActiveTrue(Integer clientId);

    Page<Clients> findByActiveTrue(Pageable pageable);

    Clients findByUserNameAndPassword(String username, String password);

}
