package com.acerete.services.message.response;

public class ExceptionServiceResponse implements ServiceResponse {
	
	private final String errorMessage;

	public ExceptionServiceResponse(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public Response getResponse() {
		return new Response(ResponseType.EXCEPTION, errorMessage);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExceptionResponse [errorMessage=").append(errorMessage).append("]");
		return builder.toString();
	}
}
