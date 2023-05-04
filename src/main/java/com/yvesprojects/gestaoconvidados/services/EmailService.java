package com.yvesprojects.gestaoconvidados.services;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.yvesprojects.gestaoconvidados.exceptions.AuthorizationException;
import com.yvesprojects.gestaoconvidados.models.EmailDetails;
import com.yvesprojects.gestaoconvidados.models.MailGuestDetails;
import com.yvesprojects.gestaoconvidados.models.User;
import com.yvesprojects.gestaoconvidados.models.dto.EmailDetailsAttachmentDTO;
import com.yvesprojects.gestaoconvidados.models.dto.EmailDetailsTemplateDTO;
import com.yvesprojects.gestaoconvidados.models.projection.EmailDetailsProjection;
import com.yvesprojects.gestaoconvidados.repositories.EmailDetailsRepository;
import com.yvesprojects.gestaoconvidados.security.UserSpringSecurity;

import freemarker.template.Configuration;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private UserService userService;

	@Autowired
	private EmailDetailsRepository emailDetailsRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger( EmailService.class );

	@Autowired
	private Configuration fmConfiguration;

	@Value( "Spring.mail.username" )
	private String sender;

	public List<EmailDetailsProjection> findAllByUser() {
		UserSpringSecurity userSpringSecurity = userService.authenticated();
		if( !Objects.nonNull( userSpringSecurity ) )
			throw new AuthorizationException( "Acesso negado!" );
		List<EmailDetailsProjection> mailsDetails = this.emailDetailsRepository.findByUserId( userSpringSecurity.getId() );
		return mailsDetails;
	}

	public MailGuestDetails saveSendMail( MailGuestDetails obj ) {
		UserSpringSecurity userSpringSecurity = userService.authenticated();
		if( !Objects.nonNull( userSpringSecurity ) )
			throw new AuthorizationException( "Acesso negado!" );
		User user = this.userService.findById( userSpringSecurity.getId() );

		obj.setUser( user );
		return this.emailDetailsRepository.save( obj );
	}

	public String createMail( EmailDetails obj ) {
		UserSpringSecurity userSpringSecurity = userService.authenticated();
		if( !Objects.nonNull( userSpringSecurity ) )
			throw new AuthorizationException( "Acesso negado!" );

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom( sender );
		mailMessage.setTo( obj.getRecipient() );
		mailMessage.setText( obj.getMessageBody() );
		mailMessage.setSubject( obj.getSubject() );

		javaMailSender.send( mailMessage );
		LOGGER.info( "Email enviado com sucesso!" );
		return "Email enviado com sucesso!";
	}

	public String createMailWithAttachment( EmailDetailsAttachmentDTO obj ) throws MessagingException {
		UserSpringSecurity userSpringSecurity = userService.authenticated();
		if( !Objects.nonNull( userSpringSecurity ) )
			throw new AuthorizationException( "Acesso negado!" );

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;

		mimeMessageHelper = new MimeMessageHelper( mimeMessage, true );

		mimeMessageHelper.setFrom( sender );
		mimeMessageHelper.setTo( obj.getRecipient() );
		mimeMessageHelper.setText( obj.getMessageBody() );
		mimeMessageHelper.setSubject( obj.getSubject() );

		FileSystemResource file = new FileSystemResource( new File( obj.getAttachment() ) );
		mimeMessageHelper.addAttachment( Objects.requireNonNull( file.getFilename() ), file );
		javaMailSender.send( mimeMessage );
		LOGGER.info( "Email enviado com sucesso!" );
		return "Email enviado com sucesso!";
	}

	public void createMailWithTemplate( EmailDetailsTemplateDTO obj, Map<String, Object> propriedades ) throws MessagingException {
		UserSpringSecurity userSpringSecurity = userService.authenticated();
		if( !Objects.nonNull( userSpringSecurity ) )
			throw new AuthorizationException( "Acesso negado!" );

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;
		mimeMessageHelper = new MimeMessageHelper( mimeMessage, true );

		mimeMessageHelper.setFrom( sender );
		mimeMessageHelper.setTo( obj.getRecipient() );
		mimeMessageHelper.setSubject( obj.getSubject() );

		mimeMessageHelper.setText( getTemplateContent( propriedades ), true );
		javaMailSender.send( mimeMessageHelper.getMimeMessage() );
	}

	public String getTemplateContent( Map<String, Object> model ) {
		StringBuffer content = new StringBuffer();

		try {
			content.append( FreeMarkerTemplateUtils.processTemplateIntoString( fmConfiguration.getTemplate( "email-recuperacao-codigo.flth" ), model ) );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return content.toString();
	}
}
