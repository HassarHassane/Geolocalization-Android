package ma.hassar.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ma.hassar.demo.beans.Ami;
import ma.hassar.demo.beans.AmiId;


public interface AmiRepository extends JpaRepository<Ami, AmiId> {
	   
    @Query("SELECT a FROM Ami a WHERE a.etat=2 AND (a.id.user1Id=:id OR a.id.user2Id=:id)")
    List<Ami> findAmis(@Param("id") int id);
   
    @Query("SELECT a FROM Ami a WHERE a.etat=1 AND a.id.user2Id=:id")
    List<Ami> findInvitations(@Param("id") int id);
   
    @Query("SELECT a FROM Ami a WHERE (a.id.user1Id=:id1 AND a.id.user2Id=:id2) OR (a.id.user1Id=:id2 AND a.id.user2Id=:id1)")
    Ami findAmi(@Param("id1") int id1,@Param("id2") int id2);
}