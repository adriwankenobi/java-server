package com.acerete.exceptions;

public class HttpServerException extends CustomException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9218565084638530274L;

	public HttpServerException(String message) {
		super(message);
	}
	
	public HttpServerException(String message, Throwable cause) {
		super(message, cause);
	}

}
