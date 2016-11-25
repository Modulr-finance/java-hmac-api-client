package com.modulr.modulo.api;

import java.util.List;

public class ModuloErrorException extends RuntimeException {

	private static final long serialVersionUID = 289755368503187567L;
	
	private List<ModuloError> errors;
	
	public ModuloErrorException(List<ModuloError> errors){
		this.errors = errors;
	}

	public List<ModuloError> getErrors() {
		return errors;
	}
}
