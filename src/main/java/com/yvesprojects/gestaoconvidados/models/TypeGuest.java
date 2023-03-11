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

@Entity
@Table(name = TypeGuest.TABLE_NAME)
public class TypeGuest {
	public static final String TABLE_NAME = "type_guest";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // configura o autoincrement
	@Column(name = "type_id", unique = true)
	private Long typeId;
	
	@Column(name = "type_description", length = 30, nullable = false)
	@NotNull
	@NotEmpty
	@Size(min = 2, max = 30)
	private String typeDescription;
	
	public TypeGuest() {
	}

	public TypeGuest(Long typeId, @NotNull @NotEmpty @Size(min = 2, max = 30) String typeDescription) {
		super();
		this.typeId = typeId;
		this.typeDescription = typeDescription;
	}

	public static String getTableName() {
		return TABLE_NAME;
	}

	public Long getTypeId() {
		return typeId;
	}

	public String getTypeDescription() {
		return typeDescription;
	}

	@Override
	public int hashCode() {
		return Objects.hash(typeDescription, typeId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TypeGuest other = (TypeGuest) obj;
		return Objects.equals(typeDescription, other.typeDescription) && Objects.equals(typeId, other.typeId);
	}
}
