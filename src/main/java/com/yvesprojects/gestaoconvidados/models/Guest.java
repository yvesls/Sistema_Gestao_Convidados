package com.yvesprojects.gestaoconvidados.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = Guest.TABLE_NAME)
@AllArgsConstructor // cria o construtor automaticamente
@NoArgsConstructor // construtor vazio
@Getter
@Setter
@EqualsAndHashCode
public class Guest {
	public static final String TABLE_NAME = "guest";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // configura o autoincrement
	@Column(name = "guest_id", unique = true)
	private Long guestId;
	
	@ManyToOne
	@JoinColumn(name = "type_id", nullable = false, updatable = false)
	private TypeGuest typeGuest;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	private User user;
	
	@Column(name = "guest_name", length = 30, nullable = false)
	@NotNull
	@NotEmpty
	@Size(min = 2, max = 30)
	private String guestName;
	
	@Column(name = "guest_tel", length = 15, nullable = false)
	@NotNull
	@NotEmpty
	@Size(min = 11, max = 15)
	private String guestTel;
	
	@Column(name = "present", nullable = false)
	private Boolean present;
}
