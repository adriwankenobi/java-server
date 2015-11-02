package com.acerete.services.users;

import static org.junit.Assert.*;

import org.junit.Test;

import com.acerete.services.message.response.Response;
import com.acerete.services.message.response.ResponseType;
import com.acerete.test.SetupTest;

public class LoginServiceResponseTest extends SetupTest {

	@Test
	public void testShouldGetExceptionServiceResponse() {
		LoginServiceResponse service = new LoginServiceResponse(RESPONSE_TEXT_TEST);
		Response response = service.getResponse();
		assertEquals("Should match", response.getText(), RESPONSE_TEXT_TEST);
		assertEquals("Should match", response.getType().getId(), ResponseType.LOGIN.getId());
	}

}
