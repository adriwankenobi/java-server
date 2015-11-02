package com.acerete.services.message.request;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.acerete.exceptions.RequestNotFoundException;

public enum RequestType {
	
	/**
	 * UniqueId : Assign unique id for each MessageType
	 * Method: HTTP method (GET or POST)
	 * URLValueName: Name of the input value coming in the request
	 * POSTValueName: Name of the input value coming in the post stream
	 * ParameterNames: Name list of mandatory parameters
	 */
	LOGIN("login", RequestMethod.GET, "userId", null, Collections.<String> emptyList()),
	POST_USER_SCORE_TO_LEVEL("score", RequestMethod.POST, "levelId", "score", Arrays.asList("sessionkey")),
	GET_HIGH_SCORE_LIST_FOR_LEVEL("highscorelist", RequestMethod.GET, "levelId", null, Collections.<String> emptyList());
	
	// ERROR strings
	public final static String UNKNOWN_REQUEST_ID = "Unknown requestType";
	
	private String id;
	private RequestMethod method;
	private String urlValueName;
	private String postValueName;
	private List<String> parameterNames;
	
	private RequestType(String id, RequestMethod method, String urlValueName, String postValueName, List<String> parameterNames) {
		this.id = id;
		this.method = method;
		this.urlValueName = urlValueName;
		this.postValueName = postValueName;
		this.parameterNames = parameterNames;
	}

	public String getId() {
		return id;
	}
	
	public RequestMethod getMethod() {
		return method;
	}
	
	public String getURLValueName() {
		return urlValueName;
	}
	
	public String getPostValueName() {
		return postValueName;
	}
	
	public List<String> getParameterNames() {
		return parameterNames;
	}
	
	public static RequestType getById(String id) throws RequestNotFoundException {
		for (RequestType messageType : RequestType.values()) {
			if (messageType.getId().equals(id)) {
				return messageType;
			}
		}
		throw new RequestNotFoundException(UNKNOWN_REQUEST_ID);
	}
	
}
