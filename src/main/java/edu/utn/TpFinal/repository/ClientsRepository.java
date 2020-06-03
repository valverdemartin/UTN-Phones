package edu.utn.TpFinal.repository;

import edu.utn.TpFinal.Projections.CallsGraterThan;
import edu.utn.TpFinal.Projections.DurationByMonth;
import edu.utn.TpFinal.Projections.FavouriteCall;
import edu.utn.TpFinal.model.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface ClientsRepository extends JpaRepository<Clients, Integer> {
    Optional<Clients> findByLastName(String lastName);
    /*@Query(value = "call sp_active_user (?1)", nativeQuery = true)
    void activeUser(Integer clientId);*/
    @Query(value = "SELECT u.user_name as name, u.user_last_name as lastname, dest_number as destline FROM calls " +
            "JOIN phone_lines as l " +
            "on l.id = :idLine " +
            "JOIN users as u " +
            "on l.id_client = :idClient " +
            "WHERE origin_number = :originNumber " +
            "GROUP BY dest_number " +
            "ORDER BY COUNT(dest_number) DESC " +
            "LIMIT 1;", nativeQuery = true)
    FavouriteCall favouriteCall(@Param("idLine") Integer idLine, @Param("idClient") Integer idClient, @Param("originNumber") String originNumber);

    @Query(value = "SELECT SUM(duration) as sumDuration, u.user_name as name, u.user_last_name as lastname FROM calls " +
            "JOIN users as u " +
            "on u.id = :idUser " +
            "WHERE month(call_date) = :selectedMonth ;", nativeQuery = true)
    DurationByMonth getDurationByMont(@Param("idUser") Integer idLine, @Param("selectedMonth") Integer selectedMonth);


    @Query(value = "SELECT c.dest_number as destNumber,c.duration as duration, c.total_price as totalPrice, c.call_date as callDate " +
            "FROM calls as c " +
            "JOIN phone_lines as l " +
            "ON l.id_client = :idClient " +
            "WHERE c.total_price >= :totalPrice ;", nativeQuery = true)

    List<CallsGraterThan> getCallsGreaterThan(@Param("idClient") Integer idClient, @Param("totalPrice") Double price);
}
