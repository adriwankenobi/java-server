package com.acerete.httpserver;

import java.io.IOException;
import java.io.OutputStream;

import com.acerete.Configuration;
import com.acerete.exceptions.ServicesException;
import com.acerete.exceptions.UidNotSetException;
import com.acerete.services.message.response.ExceptionServiceResponse;
import com.acerete.services.message.response.Response;
import com.acerete.services.message.response.ServiceResponse;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

public class ClientConnectionImpl implements ClientConnection {
	
	// ERROR strings
	private final static String ERROR_SENDING_RESPONSE = "Error sending response: ";
	private final static String ERROR_CLOSING_STREAM = "Error closing stream: ";
	private final static String ERROR_UID_NOT_SET = "Uid not set";
		
	private HttpExchange exchange;
	private Integer uid;

	public ClientConnectionImpl(HttpExchange exchange) {
		this.exchange = exchange;
		this.uid = null;
	}
	
	private void sendMessage(Response response, HttpStatusCode status) {
		
        OutputStream os = exchange.getResponseBody();
        try {
        	
        	String text = response.getText();
        	
        	// Send the header response
            if (text == null || (status != HttpStatusCode.OK && !Configuration.getInstance().sendErrorsToClient())) {
            	exchange.sendResponseHeaders(status.getId(), -1);
            }
            else {
            	Headers headers = exchange.getResponseHeaders();
            	if (response.getType().isCSV()) {
            		
            		// Allow download as CSV file
                    headers.add("Content-Type", "text/csv");
                    headers.add("Content-Disposition", "attachment;filename=" + response.getType().getFileName() + ".csv");
            	}
            	else {
            		
            		// Response as plain text
            		headers.add("Content-Type", "text/plain");
            	}
            	
            	exchange.sendResponseHeaders(status.getId(), text.length());
               	
               	// Send the body response
        	    os.write(text.getBytes());
            }
        }
        catch (Exception e) {
        	System.err.println(ERROR_SENDING_RESPONSE + response);
        	e.printStackTrace();
        }
        finally {
        	try {
        		os.close();
        	}
        	catch (IOException e) {
        		System.err.println(ERROR_CLOSING_STREAM + response);
        		e.printStackTrace();
        	}
        }
	}

	public void sendResponse(ServiceResponse serviceResponse) {
		sendResponse(serviceResponse, HttpStatusCode.OK);
	}
	
	private void sendResponse(ServiceResponse serviceResponse, HttpStatusCode status) {
		System.out.println("Sending response to the client: " + status.getId() + " " + serviceResponse);
		sendMessage(serviceResponse.getResponse(), status);
	}

	private void sendError(ServicesException exception, HttpStatusCode status) {
		exception.printStackTrace();
		sendResponse(new ExceptionServiceResponse(exception.getMessage()), status);
	}
	
	public void sendBadRequest(ServicesException exception) {
		System.err.println("Sending bad request to the client: " + exception.getMessage());
		sendError(exception, HttpStatusCode.BAD_REQUEST);
	}
	
	public void sendNotAuthorized(ServicesException exception) {
		System.err.println("Sending not authorized to the client: " + exception.getMessage());
		sendError(exception, HttpStatusCode.NOT_AUTHORIZED);
	}
	
	public void sendServerError(ServicesException exception) {
		System.err.println("Sending server error to the client: " + exception.getMessage());
		sendError(exception, HttpStatusCode.SERVER_ERROR);
	}
	
	public Integer getUid() throws UidNotSetException {
		if (uid == null) {
			throw new UidNotSetException(ERROR_UID_NOT_SET);
		}
		return uid;
	}
	
	public void setUid(Integer uid) {
		this.uid = uid;
	}
}
