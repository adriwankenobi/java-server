package com.acerete.services.scores;

import static org.junit.Assert.*;

import org.junit.Test;

import com.acerete.services.message.response.Response;
import com.acerete.services.message.response.ResponseType;
import com.acerete.test.SetupTest;

public class PostUserScoreToLevelServiceResponseTest extends SetupTest {

	@Test
	public void testShouldGetExceptionServiceResponse() {
		PostUserScoreToLevelServiceResponse service = new PostUserScoreToLevelServiceResponse();
		Response response = service.getResponse();
		assertEquals("Should match", response.getType().getId(), ResponseType.POST_USER_SCORE_TO_LEVEL.getId());
	}

}
