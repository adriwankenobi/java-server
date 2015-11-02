package com.acerete.httpserver;

import com.acerete.httpserver.ClientConnection;
import com.acerete.exceptions.MalformedRequestException;
import com.acerete.exceptions.NotAuthorizedUserException;
import com.acerete.exceptions.ServicesException;
import com.acerete.services.message.SecureMessage;
import com.acerete.services.message.request.Request;
import com.acerete.services.message.request.ServiceRequest;
import com.acerete.services.users.UsersServiceImpl;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class HttpRequestsHandler implements HttpHandler {
	
	// ERROR strings
	private final static String MISSING_REQUEST = "Missing request";
	private final static String WRONG_REQUEST_OBJECT = "Wrong request object";
	
	/**
	 * Creates the client connection, retrieves the request object from the HttpExchange
	 * and processes the request
	 */
	@Override
	public void handle(HttpExchange exchange) {
		
		// Create client connection
		ClientConnection clientConnection = new ClientConnectionImpl(exchange);
		
		try {
			// Read request
			Object objRequest = exchange.getAttribute(Request.REQUEST);
			if (objRequest == null) {
				throw new MalformedRequestException(MISSING_REQUEST);
			}
			if (!(objRequest instanceof Request)) {
				throw new MalformedRequestException(WRONG_REQUEST_OBJECT);
			}
			Request request = (Request) objRequest;
			System.out.println("Request: " + request);
			if (!request.isValid()) {
				throw new MalformedRequestException(request.getErrorMessage());
			}
			
			// Process request
			process(clientConnection, request);
			
		} catch (Exception e) {
			
			// Reply with an error
			ServicesException servicesException = new ServicesException(e.getMessage(), e);
			if (e instanceof MalformedRequestException) {
				clientConnection.sendBadRequest(servicesException);
			}
			else if (e instanceof NotAuthorizedUserException) {
				clientConnection.sendNotAuthorized(servicesException);
			}
			else {
				clientConnection.sendServerError(servicesException);
			}
		}
	}

	private void process(ClientConnection clientConnection, Request request) {
		
		// Get service request
		ServiceRequest serviceRequest = request.getServiceRequest();
		
		// Update connection if secured
		if (serviceRequest instanceof SecureMessage) {
			SecureMessage secureMessage = (SecureMessage) serviceRequest;
			String sessionKey = secureMessage.getSessionKey();
			Integer uid = UsersServiceImpl.getInstance().getUidBySessionKey(sessionKey);
			clientConnection.setUid(uid);
		}
		
		// Handle request
		serviceRequest.handle(clientConnection);
	}
}
