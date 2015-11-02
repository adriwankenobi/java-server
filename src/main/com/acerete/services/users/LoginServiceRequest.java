package com.acerete.services.users;

import com.acerete.httpserver.ClientConnection;
import com.acerete.services.message.request.ServiceRequest;

public class LoginServiceRequest implements ServiceRequest {

	private final Integer userId;
	
	public LoginServiceRequest(Integer userId) {
		this.userId = userId;
	}
	
	public Integer getUserId() {
		return userId;
	}
	
	@Override
	public void handle(ClientConnection clientConnection) {
		UsersServiceImpl.getInstance().handleLogin(clientConnection, this);
	}
 
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoginRequest [userId=").append(userId).append("]");
		return builder.toString();
	}
}