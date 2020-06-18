package edu.utn.TpFinal.repository;

import edu.utn.TpFinal.Projections.TopCalls;
import edu.utn.TpFinal.Projections.UserCalls;
import edu.utn.TpFinal.model.Calls;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface CallsRepository extends JpaRepository <Calls, Integer>{

   @Query(value = "SELECT c.dest_number as destNumber,c.duration as duration, c.total_price as totalPrice, c.call_date as callDate " +
            "FROM calls as c " +
            "WHERE c.id_origin_line = :lineId AND c.call_date BETWEEN :from AND :to " +
            "ORDER BY c.call_date /*#pageable*/ ;",
           countQuery = "SELECT COUNT(c.call_date) FROM calls as c;",
           nativeQuery = true)
    public Page<UserCalls> getCallsByUser(Pageable pageable, @Param("lineId") Integer lineId, @Param("from") Timestamp from, @Param("to") Timestamp to);

    @Query(value = "SELECT c.dest_number as destNumber, COUNT(c.dest_number) as count " +
            "FROM calls as c " +
            "WHERE c.id_origin_line = :idLine " +
            "GROUP BY c.dest_number " +
            "Order by COUNT(c.dest_number) DESC Limit 10;"
            , nativeQuery = true)
    List<TopCalls> getFavouritesCalls(@Param("idLine") Integer idLine);

    Boolean existsByOriginNumberAndCallDate(String originNumber, Timestamp callDate);

}
