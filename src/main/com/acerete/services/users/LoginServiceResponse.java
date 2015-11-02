package com.acerete.services.users;

import com.acerete.services.message.response.Response;
import com.acerete.services.message.response.ResponseType;
import com.acerete.services.message.response.ServiceResponse;

public class LoginServiceResponse implements ServiceResponse {
	
	String sessionKey;
	
	public LoginServiceResponse(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	@Override
	public Response getResponse() {
		return new Response(ResponseType.LOGIN, sessionKey);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoginResponse [sessionKey=").append(sessionKey).append("]");
		return builder.toString();
	}

}
