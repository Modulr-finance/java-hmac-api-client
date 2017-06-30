package com.modulr.modulo.api;

import java.util.Date;
import java.util.UUID;

public class SecurityParametersSupplier {
	
	public Date getDate(){
		return new Date();
	}

	public String getNonce(){
		return UUID.randomUUID().toString();
	}
	
	public boolean getRetry(){
		return false;
	}
}
