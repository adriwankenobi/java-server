package com.acerete.exceptions;

public class CustomException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -660939765669486861L;

	public CustomException(String message) {
		super(message);
	}
	
	public CustomException(String message, Throwable cause) {
		super(message, cause);
	}

}
