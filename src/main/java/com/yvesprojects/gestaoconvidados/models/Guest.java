package com.yvesprojects.gestaoconvidados.models;

import java.util.Objects;

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

@Entity
@Table(name = Guest.TABLE_NAME)
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
	
	@Column(name = "present", nullable = false)
	private Boolean present;
	
	public Guest() {
	}

	public Guest(Long guestId, TypeGuest typeGuest, User user,
			@NotNull @NotEmpty @Size(min = 2, max = 30) String guestName, Boolean present) {
		super();
		this.guestId = guestId;
		this.typeGuest = typeGuest;
		this.user = user;
		this.guestName = guestName;
		this.present = present;
	}

	public Long getGuestId() {
		return guestId;
	}

	public void setGuestId(Long guestId) {
		this.guestId = guestId;
	}

	public TypeGuest getTypeGuest() {
		return typeGuest;
	}

	public void setTypeGuest(TypeGuest typeGuest) {
		this.typeGuest = typeGuest;
	}

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public Boolean getPresent() {
		return present;
	}

	public void setPresent(Boolean present) {
		this.present = present;
	}

	@Override
	public int hashCode() {
		return Objects.hash(guestId, guestName, present, typeGuest, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Guest other = (Guest) obj;
		return Objects.equals(guestId, other.guestId) && Objects.equals(guestName, other.guestName)
				&& Objects.equals(present, other.present) && Objects.equals(typeGuest, other.typeGuest)
				&& Objects.equals(user, other.user);
	}
}
