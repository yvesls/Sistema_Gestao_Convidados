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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = Guest.TABLE_NAME )
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Guest {

	public static final String TABLE_NAME = "guest";

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY ) // configura o autoincrement
	@Column( name = "guest_id", unique = true )
	private Long guestId;

	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "type_id", nullable = false, updatable = false )
	private TypeGuest typeGuest;

	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "user_id", nullable = false, updatable = false )
	private User user;

	@Column( name = "guest_name", length = 30, nullable = false )
	@NotBlank
	@Size( min = 2, max = 30 )
	private String guestName;

	@Column( name = "guest_email", length = 255, nullable = false, unique = true )
	@NotBlank
	private String guestEmail;

	@Column( name = "guest_tel", length = 15, nullable = false )
	@NotBlank
	@Size( min = 11, max = 15 )
	private String guestTel;

	@Column( name = "present", nullable = false )
	private Boolean present;
}
