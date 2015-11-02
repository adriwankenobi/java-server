package com.acerete.services.message.response;

import static org.junit.Assert.*;

import org.junit.Test;

import com.acerete.test.SetupTest;

public class ExceptionServiceResponseTest extends SetupTest {

	@Test
	public void testShouldGetExceptionServiceResponse() {
		ExceptionServiceResponse service = new ExceptionServiceResponse(RESPONSE_TEXT_TEST);
		Response response = service.getResponse();
		assertEquals("Should match", response.getText(), RESPONSE_TEXT_TEST);
		assertEquals("Should match", response.getType().getId(), ResponseType.EXCEPTION.getId());
	}

}
