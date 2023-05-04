package com.yvesprojects.gestaoconvidados.models.dto;

import com.yvesprojects.gestaoconvidados.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaveSendMailDTO {

	private Long guestId;

	private Long emailId;

	private User user;
}
