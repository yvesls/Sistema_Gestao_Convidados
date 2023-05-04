package com.yvesprojects.gestaoconvidados.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailDetailsTemplateDTO {

	private String recipient;

	private String messageBody;

	private String guestName;

	private String subject;

	private String image;
}
