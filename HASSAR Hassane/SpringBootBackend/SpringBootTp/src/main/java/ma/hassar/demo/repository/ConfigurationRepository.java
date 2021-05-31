package ma.hassar.demo.repository;

import java.sql.Timestamp;
import java.util.List;

 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ma.hassar.demo.beans.Configuration;

 



 

public interface ConfigurationRepository extends JpaRepository<Configuration, Integer>  {
    Configuration findById(int id);
    
    @Query("select c from Configuration c Where c.user.id = :id")
    Configuration findConfigByUserId(@Param("id") int id);

 

}