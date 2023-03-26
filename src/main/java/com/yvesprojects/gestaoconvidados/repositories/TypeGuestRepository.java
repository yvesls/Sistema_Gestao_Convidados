package com.yvesprojects.gestaoconvidados.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yvesprojects.gestaoconvidados.models.TypeGuest;
import com.yvesprojects.gestaoconvidados.models.projection.TypeGuestProjection;

@Repository
public interface TypeGuestRepository extends JpaRepository<TypeGuest, Long> {
	
	@Query(value = "SELECT tg FROM TypeGuest tg WHERE tg.user.id = :user_id") // forma do spring
	List<TypeGuestProjection> findByUserId(@Param("user_id") Long userId);
}
