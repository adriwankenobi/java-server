package com.acerete.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.acerete.exceptions.MalformedRequestException;
import com.acerete.httpserver.ParamsFilter;
import com.acerete.services.message.request.RequestMethod;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

public class TestClient {
	
	// ERROR strings
	private final static String WRONG_METHOD = "Wrong HTTP method";
		
	private final String baseURL;
	
	public TestClient(String baseURL){
		this.baseURL = baseURL;
	}
	
	public Request buildRequest(RequestMethod method, String path, String postStream) throws Exception {

		String fullURI = baseURL + path;
		System.out.println("Sending request: " + method.getId() + " " + fullURI + " stream: " + postStream);
		
		Request request = null;
		switch (method) {
		case GET:
			request = Request.Get(fullURI);
			break;
		case POST:
			request = Request.Post(fullURI);
			if (postStream != null) {
				request.bodyString(postStream, ContentType.TEXT_PLAIN);
			}
			break;
		case PUT:
			request = Request.Put(fullURI);
			break;
		case DELETE:
			request = Request.Delete(fullURI);
			break;	
		default:
			throw new MalformedRequestException(WRONG_METHOD);
		}
		
		return request;
	}
	
	public HttpResponse send(RequestMethod method, String path, String postStream) throws Exception {

		Request request = buildRequest(method, path, postStream);
		return request.execute().returnResponse();
	}
	
	public int getStatusCode(HttpResponse httpResponse) {
		return httpResponse.getStatusLine().getStatusCode();
	}
	
	public String getResponseText(HttpResponse httpResponse) throws Exception {
		InputStreamReader reader = new InputStreamReader(httpResponse.getEntity().getContent(), ParamsFilter.UTF8);
        BufferedReader br = new BufferedReader(reader);
		return br.readLine();
	}

}