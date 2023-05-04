package com.yvesprojects.gestaoconvidados.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yvesprojects.gestaoconvidados.models.MailGuestDetails;
import com.yvesprojects.gestaoconvidados.models.projection.EmailDetailsProjection;

@Repository
public interface EmailDetailsRepository extends JpaRepository<MailGuestDetails, Long> {

	List<EmailDetailsProjection> findByUserId( Long id );
}
