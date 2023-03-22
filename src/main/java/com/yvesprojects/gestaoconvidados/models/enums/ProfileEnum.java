package com.yvesprojects.gestaoconvidados.models.enums;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProfileEnum {
	ADMIN(1, "ROLE_ADMIN"),
	USER(2, "ROLE_USER");
	
	private Integer code;
	private String description;
	
	public static ProfileEnum toEnum(Integer code) {
		if(Objects.isNull(code))
			return null;
		for(ProfileEnum en: ProfileEnum.values()) {
			if(code.equals(en.getCode())) 
				return en;
		}
		throw new IllegalArgumentException("Invalid code: " + code);
	}
}
