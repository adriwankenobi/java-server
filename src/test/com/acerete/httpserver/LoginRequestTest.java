package com.acerete.httpserver;

import static org.junit.Assert.*;
import org.junit.Test;

import com.acerete.data.sessions.SessionsDataImpl;
import com.acerete.services.message.request.RequestMethod;
import com.acerete.services.message.request.RequestType;
import com.acerete.vo.Session;

public class LoginRequestTest extends HttpRequestValidationTest {

	private final static RequestType LOGIN = RequestType.LOGIN;
	
	@Test
	public void testShouldNotBePost() throws Exception {
		testErrorValidation(RequestMethod.POST, "/1/"+LOGIN.getId(), null, HttpStatusCode.BAD_REQUEST, ParamsFilter.WRONG_METHOD);
	}
	
	@Test
	public void testShouldLogin() throws Exception {
		int uid = 9999;
		String response = testOkValidation(RequestMethod.GET, "/"+uid+"/"+LOGIN.getId(), null);
		assertNotNull("Should not be null", response);
		assertFalse("Should not be empty", response.isEmpty());
		assertTrue("Should be session key", response.matches("[0-9a-z]+"));
		
		Session session = SessionsDataImpl.getInstance().getBySessionKey(response);
		assertNotNull("Should not be null", session);
		assertEquals("Should match", session.getUid().intValue(), uid);
	}

}
