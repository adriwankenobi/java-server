package com.acerete.services.message.response;

public class Response {
	
	private final ResponseType type;
	private final String text;
	
	public Response(ResponseType type, String text) {
		this.type = type;
		this.text = text;
	}
	
	public ResponseType getType() {
		return type;
	}
	
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Response [type=").append(type)
				.append(", text=").append(text).append("]");
		return builder.toString();
	}
}
