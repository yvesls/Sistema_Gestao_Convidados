package com.yvesprojects.gestaoconvidados.models;

import java.util.Objects;

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

@Entity
@Table(name = User.TABLE_NAME)
public class User {
	public interface CreateUser{}
	
	public interface UpdateUser{}
	
	public static final String TABLE_NAME = "user";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // configura o autoincrement
	@Column(name = "id", unique = true)
	private Long id;
	
	@Column(name = "user_name", length = 30, nullable = false)
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
	
	public User() {
	}

	public User(Long userId,
			@NotNull(groups = CreateUser.class) @NotEmpty(groups = CreateUser.class) @Size(groups = CreateUser.class, min = 2, max = 30) String userName,
			@NotNull(groups = { CreateUser.class, UpdateUser.class }) @NotEmpty(groups = { CreateUser.class,
					UpdateUser.class }) @Size(groups = { CreateUser.class,
							UpdateUser.class }, min = 8, max = 60) String password) {
		super();
		this.id = userId;
		this.userName = userName;
		this.password = password;
	}

	public static String getTableName() {
		return TABLE_NAME;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long userId) {
		this.id = userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(password, id, userName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(password, other.password) && Objects.equals(id, other.id)
				&& Objects.equals(userName, other.userName);
	}
}
