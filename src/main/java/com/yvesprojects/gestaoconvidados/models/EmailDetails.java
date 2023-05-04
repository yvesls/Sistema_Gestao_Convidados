package com.yvesprojects.gestaoconvidados.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmailDetails {

	private String recipient;

	private String messageBody;

	private String subject;

}
