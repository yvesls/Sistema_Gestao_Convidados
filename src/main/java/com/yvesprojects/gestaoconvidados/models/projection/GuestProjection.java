package com.yvesprojects.gestaoconvidados.models.projection;

import com.yvesprojects.gestaoconvidados.models.TypeGuest;

public interface GuestProjection {

	public Long getGuestId();

	public String getGuestEmail();

	public TypeGuest getTypeGuest();

	public String getGuestName();

	public String getGuestTel();

	public Boolean getPresent();
}
