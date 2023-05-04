package com.yvesprojects.gestaoconvidados.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yvesprojects.gestaoconvidados.models.EmailDetails;
import com.yvesprojects.gestaoconvidados.models.Guest;
import com.yvesprojects.gestaoconvidados.models.MailGuestDetails;
import com.yvesprojects.gestaoconvidados.models.dto.EmailDetailsAttachmentDTO;
import com.yvesprojects.gestaoconvidados.models.dto.EmailDetailsTemplateDTO;
import com.yvesprojects.gestaoconvidados.models.projection.EmailDetailsProjection;
import com.yvesprojects.gestaoconvidados.services.EmailService;
import com.yvesprojects.gestaoconvidados.services.GuestService;

@RestController
@RequestMapping( "/email" )
@Validated
public class EmailController {

	@Autowired
	private EmailService emailService;

	@Autowired
	private GuestService guestService;

	@PostMapping( "/sendMail" )
	public ResponseEntity<Void> sendMail( @RequestBody EmailDetails obj ) {
		emailService.createMail( obj );
		return ResponseEntity.noContent().build();
	}

	@PostMapping( "/sendMailWithAttachment" )
	public ResponseEntity<Void> sendMailWithAttachment( @RequestBody EmailDetailsAttachmentDTO obj ) {
		try {
			emailService.createMailWithAttachment( obj );
		} catch ( MessagingException e ) {
			e.printStackTrace();
		}
		return ResponseEntity.noContent().build();
	}

	@PostMapping( "/sendMailWithTemplate" )
	public ResponseEntity<Void> sendMailWithTemplate( @RequestBody EmailDetailsTemplateDTO obj ) {
		Guest g = new Guest();
		g.setGuestEmail( obj.getRecipient() );

		try {
			Guest guest = guestService.findByGuestEmail( g );
			obj.setRecipient( guest.getGuestEmail() );

			Map<String, Object> propriedades = new HashMap<>();
			propriedades.put( "nome", obj.getGuestName() );
			propriedades.put( "mensagem", obj.getMessageBody() );
			propriedades.put( "imagem", obj.getImage() );
			emailService.createMailWithTemplate( obj, propriedades );
			if( saveMailDetails( guest ) == null ) {
				throw new MessagingException( "Erro ao salvar o registro de email enviado." );
			}
		} catch ( MessagingException e ) {
			e.printStackTrace();
		}
		return ResponseEntity.noContent().build();
	}

	public MailGuestDetails saveMailDetails( Guest guest ) {
		MailGuestDetails saveMailDetails = new MailGuestDetails();
		saveMailDetails.setGuest( guest );
		return emailService.saveSendMail( saveMailDetails );
	}

	@GetMapping( "/user" )
	public ResponseEntity<List<EmailDetailsProjection>> findAllByUser() {
		List<EmailDetailsProjection> objs = emailService.findAllByUser();
		return ResponseEntity.ok().body( objs );
	}
}
