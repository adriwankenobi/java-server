package com.acerete.httpserver;

import static org.junit.Assert.*;

import org.apache.http.HttpResponse;
import org.junit.Test;

import com.acerete.services.message.request.RequestMethod;
import com.acerete.services.message.request.RequestType;
import com.acerete.test.SetupIntegrationTest;

public class HttpRequestValidationTest extends SetupIntegrationTest {

	@Test
	public void testShouldNotSupportPUTMethod() throws Exception {
		testErrorValidation(RequestMethod.PUT, "/", null, HttpStatusCode.BAD_REQUEST, ParamsFilter.WRONG_METHOD);
	}
	
	@Test
	public void testShouldNotSupportDELETEMethod() throws Exception {
		testErrorValidation(RequestMethod.DELETE, "/", null, HttpStatusCode.BAD_REQUEST, ParamsFilter.WRONG_METHOD);
	}
	
	@Test
	public void testShouldNotSupportLessThan3EncodedParameters() throws Exception {
		testErrorValidation(RequestMethod.GET, "/1", null, HttpStatusCode.BAD_REQUEST, ParamsFilter.WRONG_URI);
	}
	
	@Test
	public void testShouldNotSupportMoreThan3EncodedParameters() throws Exception {
		testErrorValidation(RequestMethod.GET, "/1/2/3", null, HttpStatusCode.BAD_REQUEST, ParamsFilter.WRONG_URI);
	}
	
	@Test
	public void testShouldNotSupportNotIntegerFirstParameter() throws Exception {
		testErrorValidation(RequestMethod.GET, "/NaN/2", null, HttpStatusCode.BAD_REQUEST, ParamsFilter.WRONG_URI);
	}
	
	@Test
	public void testShouldNotSupportNegativeFirstParameter() throws Exception {
		testErrorValidation(RequestMethod.GET, "/-1/2", null, HttpStatusCode.BAD_REQUEST, ParamsFilter.WRONG_URI);
	}
	
	@Test
	public void testShouldNotSupportNonRequestSecondParameter() throws Exception {
		testErrorValidation(RequestMethod.GET, "/1/invalid", null, HttpStatusCode.BAD_REQUEST, RequestType.UNKNOWN_REQUEST_ID);
	}
	
	public void testErrorValidation(RequestMethod method, String path, String postStream, 
			HttpStatusCode expectedCode, String expectedErrorResponse) throws Exception {
		
		HttpResponse response = testClient.send(method, path, postStream);
		assertEquals("Should expect bad request", testClient.getStatusCode(response), expectedCode.getId());
		assertEquals("Should expect error", testClient.getResponseText(response), expectedErrorResponse);
	}
	
	public String testOkValidation(RequestMethod method, String path, String postStream) throws Exception {
		HttpResponse response = testClient.send(method, path, postStream);
		assertEquals("Should expect ok request", testClient.getStatusCode(response), HttpStatusCode.OK.getId());
		return testClient.getResponseText(response);
	}
	
	public String testNoResponseValidation(RequestMethod method, String path, String postStream) throws Exception {
		HttpResponse response = testClient.send(method, path, postStream);
		
		// Wait 1 second for request to be completed
		Thread.sleep(1000);
		
		assertEquals("Should expect ok request", testClient.getStatusCode(response), HttpStatusCode.OK.getId());
		return testClient.getResponseText(response);
	}
	
}	