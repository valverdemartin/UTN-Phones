package edu.utn.TpFinal.repository;

import edu.utn.TpFinal.Projections.FavouriteCall;
import edu.utn.TpFinal.model.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface ClientsRepository extends JpaRepository<Clients, Integer> {
    Optional<Clients> findByLastName(String lastName);
    /*@Query(value = "call sp_active_user (?1)", nativeQuery = true)
    void activeUser(Integer clientId);*/
    @Query(value = "SELECT u.user_name, u.user_last_name, dest_number FROM calls " +
            "JOIN phone_lines as l " +
            "on l.id = :idLine " +
            "JOIN users as u " +
            "on l.id_client = :idClient " +
            "WHERE origin_number = :originNumber " +
            "GROUP BY dest_number " +
            "ORDER BY COUNT(dest_number) DESC " +
            "LIMIT 1;", nativeQuery = true)
    FavouriteCall favouriteCall(@Param("idLine") Integer idLine, @Param("idClient") Integer idClient, @Param("originNumber") String originNumber);
}
