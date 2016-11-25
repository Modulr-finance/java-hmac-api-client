package com.modulr.modulo.api;

/**
 * Represents a single error item
 * @author ritesh.tendulkar
 *
 */
public class ModuloError {

	private String field;
	private String code;
	private String message;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}