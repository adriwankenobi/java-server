package com.acerete.services.message.request;

import java.util.HashMap;
import java.util.Map;

import com.acerete.exceptions.RequestNotFoundException;
import com.acerete.services.scores.GetHighScoreForLevelServiceRequest;
import com.acerete.services.scores.PostUserScoreToLevelServiceRequest;
import com.acerete.services.users.LoginServiceRequest;

public class Request {
	
	// ERROR strings
	private final static String UNKNOWN_REQUEST = "Unknown requestType with Id :";
	
	// Attribute name
	public final static String REQUEST = "request";
	
	private final RequestType type;
	private final Map<String, Object> parameters;
	private final boolean isValid;
	private final String errorMessage;
	
	public Request(RequestType type) {
		this.type = type;
		this.parameters = new HashMap<String, Object>();
		this.isValid = true;
		this.errorMessage = null;
	}
	
	public Request(String error) {
		this.type = null;
		this.parameters = null;
		this.isValid = false;
		this.errorMessage = error;
	}
	
	public void addParameter(String name, Object value) {
		if (isValid) {
			this.parameters.put(name, value);
		}
	}
	
	public RequestType getType() {
		return type;
	}
	
	public Map<String, Object> getParameters() {
		return parameters;
	}
	
	public boolean isValid() {
		return isValid;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public ServiceRequest getServiceRequest() {
		switch(type) {
		case LOGIN:
			return new LoginServiceRequest(
					(Integer) parameters.get(type.getURLValueName()) );
		case POST_USER_SCORE_TO_LEVEL:
			return new PostUserScoreToLevelServiceRequest(
					(String) parameters.get(type.getParameterNames().iterator().next()),
					(Integer) parameters.get(type.getURLValueName()),
					(Integer) parameters.get(type.getPostValueName()) );
		case GET_HIGH_SCORE_LIST_FOR_LEVEL:
			return new GetHighScoreForLevelServiceRequest(
					(Integer) parameters.get(type.getURLValueName()) );
		default:
			throw new RequestNotFoundException(UNKNOWN_REQUEST);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (isValid) {
			builder.append("Request [type=").append(type)
			.append(", parameters=").append(parameters).append("]");
		}
		else {
			builder.append("Request [error=").append(errorMessage).append("]");
		}
		return builder.toString();
	}
}
