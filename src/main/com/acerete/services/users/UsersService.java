package com.acerete.services.users;

import com.acerete.exceptions.NotAuthorizedUserException;
import com.acerete.httpserver.ClientConnection;
import com.acerete.services.Service;

public interface UsersService extends Service {

	/**
	 * Handles the login action
	 * @param clientConnection
	 * @param loginRequest
	 */
	public void handleLogin(ClientConnection clientConnection, LoginServiceRequest loginRequest);
	
	/**
	 * Gets user id from session key
	 * @param sessionKey
	 * @return
	 * @throws NotAuthorizedUserException
	 */
	public Integer getUidBySessionKey(String sessionKey) throws NotAuthorizedUserException;
}
