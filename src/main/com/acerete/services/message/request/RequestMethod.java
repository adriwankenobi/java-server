package com.acerete.services.message.request;

public enum RequestMethod {

	GET("GET"),
	POST("POST"),
	PUT("PUT"),
	DELETE("DELETE");
	
	private String id;
	
	private RequestMethod(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
}
