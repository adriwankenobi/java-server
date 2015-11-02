package com.acerete.httpserver;

public enum HttpStatusCode {

	OK(200),
	BAD_REQUEST(400),
	NOT_AUTHORIZED(401),
	SERVER_ERROR(500);
	
	private int id;
	
	private HttpStatusCode(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
}
