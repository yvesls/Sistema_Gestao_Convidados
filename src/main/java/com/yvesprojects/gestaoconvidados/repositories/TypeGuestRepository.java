package com.yvesprojects.gestaoconvidados.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yvesprojects.gestaoconvidados.models.TypeGuest;

@Repository
public interface TypeGuestRepository extends JpaRepository<TypeGuest, Long> {
	
}
