package com.yvesprojects.gestaoconvidados.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailDetailsAttachmentDTO {

	private String recipient;

	private String messageBody;

	private String subject;

	private String attachment;
}
