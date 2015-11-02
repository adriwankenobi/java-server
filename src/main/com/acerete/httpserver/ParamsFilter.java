package com.acerete.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import com.acerete.exceptions.MalformedRequestException;
import com.acerete.services.message.request.Request;
import com.acerete.services.message.request.RequestMethod;
import com.acerete.services.message.request.RequestType;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

public class ParamsFilter extends Filter {

	// Description
	private final static String DESCRIPTION = "Filter requests";
	
	// ERROR strings
	public final static String WRONG_METHOD = "Wrong HTTP method";
	public final static String WRONG_URI = "Wrong URI";
	public final static String WRONG_PARAMETERS = "Wrong parameters in URI query";
	public final static String WRONG_POST_STREAM = "Wrong POST stream";
	
	// POST stream codification
	public final static String UTF8 = "UTF-8";

	/**
	 * Will set a Request object as an attribute in the HttpExchange object.
	 * Validates the correct input values (URL encoded, query parameters and POST stream).
	 */
    @Override
    public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
        
    	Request request = null;
    	
    	try {
	    	// Get requested URI
	    	URI requestedURI = exchange.getRequestURI();
	    	String method = exchange.getRequestMethod();
	        System.out.println("Requested URI: " + method + " " + requestedURI);
	        
	        // Only GET and POST supported
	        if (!method.equals(RequestMethod.GET.getId()) && !method.equals(RequestMethod.POST.getId())) {
	        	throw new MalformedRequestException(WRONG_METHOD);
	        }
	        else {
	        	// Get URL encoded parameters
	        	String[] tokens = requestedURI.getPath().split("/");
		        if (tokens.length != 3 || !tokens[0].equals("")) {
		        	throw new MalformedRequestException(WRONG_URI);
		        }
		        else {
		        	
		        	// Read input value
		        	Integer inputValue = null;
		        	try {
			        	inputValue = Integer.parseInt(tokens[1]);
			        	if (inputValue < 0) {
			        		throw new NumberFormatException();
			        	}
		        	}
		        	catch (NumberFormatException e) {
		        		throw new MalformedRequestException(WRONG_URI);
		        	}
		        	
		        	// Read and validate request type id
		        	RequestType requestType = RequestType.getById(tokens[2]);
		        	
		        	// Validate method
		        	if (!requestType.getMethod().getId().equals(method)) {
		        		throw new MalformedRequestException(WRONG_METHOD);
		        	}
		        	
		        	// Create request object
		        	request = new Request(requestType);
		        	
		        	// Add query parameters
		        	String query = requestedURI.getQuery();
		        	if (query != null) {
			        	String[] parameters = query.split("&");
			        	for (String parameter : parameters) {
			        		String[] keyValuePair = parameter.split("=");
			        		if (keyValuePair.length != 2) {
			        			throw new MalformedRequestException(WRONG_PARAMETERS);
			        		}
			        		request.addParameter(keyValuePair[0].toLowerCase(), keyValuePair[1]);
			        	}
		        	}
		        	
		        	// Validate parameters
		        	for (String mandatoryParameter : requestType.getParameterNames()) {
		        		if (!request.getParameters().containsKey(mandatoryParameter)) {
		        			throw new MalformedRequestException(WRONG_PARAMETERS);
		        		}
		        	}
		        	
		        	// Add input value as another parameter
		        	request.addParameter(requestType.getURLValueName(), inputValue);
		        	
		        	// Read and validate POST stream
		        	if (method.equals(RequestMethod.POST.getId())) {
		                InputStreamReader reader = new InputStreamReader(exchange.getRequestBody(), UTF8);
		                BufferedReader br = new BufferedReader(reader);
		                Integer postValue = null;
		                try {
		                	postValue = Integer.parseInt(br.readLine());
		                	if (br.readLine() != null) {
		                		throw new NumberFormatException();
		                	}
		                	if (postValue < 0) {
			        			throw new NumberFormatException();
			        		}
		                }	
		                catch (NumberFormatException e) {
		                	throw new MalformedRequestException(WRONG_POST_STREAM);
		                }
		                // Add post value as another parameter
		                request.addParameter(requestType.getPostValueName(), postValue);
		            }
		        }
	        }
    	}
    	catch (Exception e) {
    		System.err.println(e.getMessage());
    		e.printStackTrace();
    		request = new Request(e.getMessage());
    	}
    	finally {
    		// Put request as an attribute
        	exchange.setAttribute(Request.REQUEST, request);
        	
    		chain.doFilter(exchange);
    	}
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }

}
