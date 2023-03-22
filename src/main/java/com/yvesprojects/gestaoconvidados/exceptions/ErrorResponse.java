package com.yvesprojects.gestaoconvidados.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
	private final int status;
	private final String message;
	private String stackTrace;
	private List<ValidationError> errors; 
	
	private static class ValidationError {
		private final String fields;
		private final String message;
		public ValidationError(String fields, String message) {
			super();
			this.fields = fields;
			this.message = message;
		}
	}
	
	public void addValidationError(String field, String message) {
		if(Objects.isNull(errors)) {
			this.errors = new ArrayList<>();
		}
		this.errors.add(new ValidationError(field, message));
	}

	public ErrorResponse(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public List<ValidationError> getErrors() {
		return errors;
	}

	public void setErrors(List<ValidationError> errors) {
		this.errors = errors;
	}

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public String getStackTrace() {
		return stackTrace;
	}
}
