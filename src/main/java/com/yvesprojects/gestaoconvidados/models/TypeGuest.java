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
@Table(name = TypeGuest.TABLE_NAME)
@AllArgsConstructor // cria o construtor automaticamente
@NoArgsConstructor // construtor vazio
@Getter
@Setter
@EqualsAndHashCode
public class TypeGuest {
	public static final String TABLE_NAME = "type_guest";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // configura o autoincrement
	@Column(name = "type_id", unique = true)
	private Long typeId;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	private User user;
	
	@Column(name = "type_description", length = 30, nullable = false, unique = true)
	@NotNull
	@NotEmpty
	@Size(min = 2, max = 30)
	private String typeDescription;
}
