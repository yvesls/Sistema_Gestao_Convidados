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
@Table( name = TypeGuest.TABLE_NAME )
@AllArgsConstructor // cria o construtor automaticamente
@NoArgsConstructor // construtor vazio
@Data
public class TypeGuest {

	public static final String TABLE_NAME = "type_guest";

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY ) // configura o autoincrement
	@Column( name = "type_id", unique = true )
	private Long typeId;

	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "user_id", nullable = false, updatable = false )
	private User user;

	@Column( name = "type_description", length = 30, nullable = false )
	@NotBlank
	@Size( min = 2, max = 30 )
	private String typeDescription;
}
