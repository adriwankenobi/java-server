package com.acerete.services.message.request;

import com.acerete.httpserver.ClientConnection;
import com.acerete.services.message.ServiceMessage;

public interface ServiceRequest extends ServiceMessage {
	
	/**
	 * Handle request
	 * @param clientConnection
	 */
	public void handle(ClientConnection clientConnection);
}
