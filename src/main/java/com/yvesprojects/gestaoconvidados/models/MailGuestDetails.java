package com.yvesprojects.gestaoconvidados.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table( name = MailGuestDetails.TABLE_NAME )
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MailGuestDetails {

	public static final String TABLE_NAME = "email_guest_details";

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "email_id", unique = true )
	private Long emailId;

	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "guest_id", nullable = false, updatable = false )
	private Guest guest;

	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "user_id", nullable = false, updatable = false )
	private User user;
}
