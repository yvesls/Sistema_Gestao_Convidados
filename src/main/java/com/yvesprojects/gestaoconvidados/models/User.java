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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.yvesprojects.gestaoconvidados.models.enums.ProfileEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = User.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor 
@Data
public class User {
	public static final String TABLE_NAME = "user";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // configura o autoincrement
	@Column(name = "id", unique = true)
	private Long id;
	
	@Column(name = "user_name", length = 30, nullable = false, unique = true)
	@NotBlank
	@Size(min = 2, max = 30)
	private String username;
	
	@JsonProperty(access = Access.WRITE_ONLY) // Define que a senha será somente para escrita, não retornando valor ao registrar
	@Column(name = "password", nullable = false)
	@NotBlank
	@Size(min = 8, max = 60)
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
