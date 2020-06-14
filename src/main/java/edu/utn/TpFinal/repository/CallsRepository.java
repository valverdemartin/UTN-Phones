package edu.utn.TpFinal.repository;
import edu.utn.TpFinal.Projections.TopCalls;
import edu.utn.TpFinal.Projections.UserCalls;
import edu.utn.TpFinal.model.Calls;
import edu.utn.TpFinal.model.Lines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface CallsRepository extends JpaRepository <Calls, Integer>{

   @Query(value = "SELECT c.dest_number as destNumber,c.duration as duration, c.total_price as totalPrice, c.call_date as callDate " +
            "FROM calls as c " +
            "WHERE c.id_origin_line = :lineId AND c.call_date BETWEEN :from AND :to ;", nativeQuery = true)
    public List<UserCalls> getCallsByUser(@Param("lineId") Integer lineId, @Param("from") Date from, @Param("to") Date to);

    @Query(value = "SELECT c.dest_number as destNumber, COUNT(c.dest_number) as count " +
            "FROM calls as c " +
            "WHERE c.id_origin_line = :idLine " +
            "GROUP BY c.dest_number " +
            "Order by COUNT(c.dest_number) DESC Limit 10;"
            , nativeQuery = true)
    List<TopCalls>getFavouritesCalls(@Param("idLine") Integer idLine);

    //List<UserCalls>findTop10ByDestLineCountByDestLine(Lines line);

    //@Query(value = "call sp(:origin,:destiny,:duration);", nativeQuery = true)
    //void insertNewCall(@Param("origin") String origin, @Param("destiny") String destiny, @Param("duration") Long duration);
}
