package com.yvesprojects.gestaoconvidados.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.yvesprojects.gestaoconvidados.models.Guest;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
	
	List<Guest> findByUser_Id(Long id); // forma simplificada
	
	//@Query(value = "SELECT g FROM Guest g WHERE g.user.id = :user_id") // forma do spring
	//List<Guest> findByUserId(@Param("user_id") Long id);
	
	//@Query(value = "SELECT * FROM Guest g WHERE g.user_id = :user_id") // com query sql
	//List<Guest> findByUserId(@Param("user_id") Long id); 
}
