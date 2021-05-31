package ma.hassar.demo.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ma.hassar.demo.beans.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findById(int id);
	@Query("select u from User u where u.telephone=:telephone")
	User findUserByTelephone(@Param("telephone") String telephone);
	@Query("select u from User u where u.imei=:imei")
	User findUserByImei(@Param("imei") String imei);

}
