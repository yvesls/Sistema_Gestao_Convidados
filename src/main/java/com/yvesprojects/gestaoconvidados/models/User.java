package com.yvesprojects.gestaoconvidados.models;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.yvesprojects.gestaoconvidados.models.enums.ProfileEnum;

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
	private String username;
	
	@JsonProperty(access = Access.WRITE_ONLY) // Define que a senha será somente para escrita, não retornando valor ao registrar
	@Column(name = "password", nullable = false)
	@NotNull(groups = {CreateUser.class, UpdateUser.class})
	@NotEmpty(groups = {CreateUser.class, UpdateUser.class})
	@Size(groups = {CreateUser.class, UpdateUser.class}, min = 8, max = 60)
	private String password;
	
	@ElementCollection(fetch = FetchType.EAGER) // sempre que buscar os usuários do banco vai buscar os perfis junto
	@JsonProperty(access = Access.WRITE_ONLY)
	@CollectionTable(name = "user_profile")
	@Column(name = "profile", nullable = false)
	private Set<Integer> profiles = new HashSet<>();// Lista de valores únicos
	
	public Set<ProfileEnum> getProfiles() {
		return this.profiles.stream().map(x->ProfileEnum.toEnum(x)).collect(Collectors.toSet()); // pega o perfil, transforma em stream, percorre, passa o valor do code e transforma em um setCollector
	}
	
	public void addProfile(ProfileEnum profileEnum) {
		this.profiles.add(profileEnum.getCode());
	}
}
