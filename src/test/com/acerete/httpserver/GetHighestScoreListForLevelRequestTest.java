package com.acerete.httpserver;

import static org.junit.Assert.*;

import org.junit.Test;

import com.acerete.services.message.request.RequestMethod;
import com.acerete.services.message.request.RequestType;

public class GetHighestScoreListForLevelRequestTest extends HttpRequestValidationTest {

	private final static RequestType GET_HIGHSCORE = RequestType.GET_HIGH_SCORE_LIST_FOR_LEVEL;
	
	@Test
	public void testShouldGetHighScore() throws Exception {
		
		// Login user 1
		Integer uid1 = 9999;
		String sessionKey1 = testOkValidation(RequestMethod.GET, "/"+uid1+"/"+RequestType.LOGIN.getId(), null);
		
		// Post score
		Integer level = 1;
		Integer score = 100;
		String parameter = RequestType.POST_USER_SCORE_TO_LEVEL.getParameterNames().iterator().next();
		testNoResponseValidation(RequestMethod.POST, "/"+level+"/"+RequestType.POST_USER_SCORE_TO_LEVEL.getId()+"?"+parameter+"="+sessionKey1, score.toString());
				
		// Login user 2
		Integer uid2 = 9998;
		String sessionKey2 = testOkValidation(RequestMethod.GET, "/"+uid2+"/"+RequestType.LOGIN.getId(), null);
		
		// Post score
		testNoResponseValidation(RequestMethod.POST, "/"+level+"/"+RequestType.POST_USER_SCORE_TO_LEVEL.getId()+"?"+parameter+"="+sessionKey2, score.toString());
				
		// Get high scores
		String response = testOkValidation(RequestMethod.GET, "/"+level+"/"+GET_HIGHSCORE.getId(), null);
		assertEquals("Should match", response, "9998=100,9999=100");
	}
	
	@Test
	public void testShouldNotBePost() throws Exception {
		testErrorValidation(RequestMethod.POST, "/1/"+GET_HIGHSCORE.getId(), null, HttpStatusCode.BAD_REQUEST, ParamsFilter.WRONG_METHOD);
	}

}
