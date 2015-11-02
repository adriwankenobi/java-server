package com.acerete.exceptions;

public class ServicesException extends CustomException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1683647070420655103L;
	
	public ServicesException(String message) {
		super(message);
	}
	
	public ServicesException(String message, Throwable cause) {
		super(message, cause);
	}

}
