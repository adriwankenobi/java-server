package com.acerete.services.message.request;

import static org.junit.Assert.*;

import java.util.Map.Entry;

import org.junit.Test;

import com.acerete.test.SetupTest;

public class RequestTest extends SetupTest {

	@Test
	public void testShouldGetRegularRequest() {
		Request request = new Request(REQUEST_TYPE_TEST);
		assertEquals("Should match", request.getType().getId(), REQUEST_TYPE_TEST.getId());
		assertTrue("Should not have parameters", request.getParameters().isEmpty());
		assertTrue("Should be valid", request.isValid());
	}
	
	@Test
	public void testShouldGetErrorRequest() {
		Request request = new Request(REQUEST_ERROR_TEST);
		assertFalse("Should be invalid", request.isValid());
		assertEquals("Should match", request.getErrorMessage(), REQUEST_ERROR_TEST);
	}
	
	@Test
	public void testShouldAddParameters() {
		Request request = new Request(REQUEST_TYPE_TEST);
		assertTrue("Should not have parameters", request.getParameters().isEmpty());
		request.addParameter(REQUEST_PARAMETER_NAME_TEST, REQUEST_PARAMETER_VALUE_TEST);
		assertEquals("Should have parameter", request.getParameters().size(), 1);
		Entry<String, Object> parameter = request.getParameters().entrySet().iterator().next();
		assertEquals("Should match name", parameter.getKey(), REQUEST_PARAMETER_NAME_TEST);
		assertEquals("Should match name", parameter.getValue().toString(), REQUEST_PARAMETER_VALUE_TEST);
	}
	
	@Test
	public void testShouldNotAddParameters() {
		Request request = new Request(REQUEST_ERROR_TEST);
		assertNull("Should not have parameters", request.getParameters());
		request.addParameter(REQUEST_PARAMETER_NAME_TEST, REQUEST_PARAMETER_VALUE_TEST);
		assertNull("Should not have parameters", request.getParameters());
	}
	
}
