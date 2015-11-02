package com.acerete.httpserver;

import com.acerete.exceptions.ServicesException;
import com.acerete.exceptions.UidNotSetException;
import com.acerete.services.message.response.ServiceResponse;

public interface ClientConnection {

	/**
	 * Sends the response to the client
	 * @param serviceResponse
	 */
	public void sendResponse(ServiceResponse serviceResponse);
	
	/**
	 * Sends BAD REQUEST to the client
	 * @param exception
	 */
	public void sendBadRequest(ServicesException exception);
	
	/**
	 * Sends NOT_AUTHORIZED to the client
	 * @param exception
	 */
	public void sendNotAuthorized(ServicesException exception);
	
	/**
	 * Sends SERVER ERROR to the client
	 * @param exception
	 */
	public void sendServerError(ServicesException exception);
	
	/**
	 * Gets the user id from the current connection
	 * @return
	 * @throws UidNotSetException
	 */
	public Integer getUid() throws UidNotSetException;
	
	/**
	 * Sets the user id into the current connection
	 * @param uid
	 */
	public void setUid(Integer uid);
}
