package com.yvesprojects.gestaoconvidados.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yvesprojects.gestaoconvidados.models.Guest;
import com.yvesprojects.gestaoconvidados.models.projection.GuestProjection;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

	// List<Guest> findByUser_Id(Long id); // forma simplificada

	List<GuestProjection> findByUserId( Long id );

	Guest findByGuestEmail( String guestEmail );

	// @Query(value = "SELECT * FROM Guest g WHERE g.user_id = :user_id") // com
	// query sql
	// List<Guest> findByUserId(@Param("user_id") Long id);
}
