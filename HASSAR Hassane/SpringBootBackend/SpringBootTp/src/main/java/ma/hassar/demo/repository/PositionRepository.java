package ma.hassar.demo.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ma.hassar.demo.beans.Position;

public interface PositionRepository extends JpaRepository<Position, Integer> {
	Position findById(int id);
	@Query("select p from Position p where p.user.id = :id AND p.date BETWEEN :date1 AND :date2")
	 Collection<?> findUserPositionByDate(@Param("id") int id,@Param("date1") Date date1,@Param("date2") Date date2);
	
	  @Query("select p from Position p Where p.user.id = :id AND TIMESTAMPDIFF(MINUTE,p.date,NOW())<=5")
	    List<Position> findLastPositionById(@Param("id") int id);
}
