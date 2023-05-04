package com.yvesprojects.gestaoconvidados.models.projection;

import com.yvesprojects.gestaoconvidados.models.Guest;

public interface EmailDetailsProjection {

	public Guest getGuest();

	public Long getEmailId();
}
