package com.yvesprojects.gestaoconvidados.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = User.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor 
@Getter
@Setter
@EqualsAndHashCode
public class User {
	public interface CreateUser{}
	
	public interface UpdateUser{}
	
	public static final String TABLE_NAME = "user";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // configura o autoincrement
	@Column(name = "id", unique = true)
	private Long id;
	
	@Column(name = "user_name", length = 30, nullable = false, unique = true)
	@NotNull(groups = CreateUser.class)
	@NotEmpty(groups = CreateUser.class)
	@Size(groups = CreateUser.class, min = 2, max = 30)
	private String userName;
	
	@JsonProperty(access = Access.WRITE_ONLY) // Define que a senha será somente para escrita, não retornando valor ao registrar
	@Column(name = "password", length = 30, nullable = false)
	@NotNull(groups = {CreateUser.class, UpdateUser.class})
	@NotEmpty(groups = {CreateUser.class, UpdateUser.class})
	@Size(groups = {CreateUser.class, UpdateUser.class}, min = 8, max = 60)
	private String password;
}
