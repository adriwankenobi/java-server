package com.acerete.httpserver;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import com.acerete.data.ranking.RankingsDataImpl;
import com.acerete.services.message.request.RequestMethod;
import com.acerete.services.message.request.RequestType;
import com.acerete.services.users.UsersServiceImpl;
import com.acerete.vo.Ranking;
import com.acerete.vo.RankingElement;

public class PostUserScoreToLevelRequestTest extends HttpRequestValidationTest {

	private final static RequestType POST_SCORE = RequestType.POST_USER_SCORE_TO_LEVEL;
	
	@Test
	public void testShouldNotBeGet() throws Exception {
		testErrorValidation(RequestMethod.GET, "/1/"+POST_SCORE.getId(), null, HttpStatusCode.BAD_REQUEST, ParamsFilter.WRONG_METHOD);
	}
	
	@Test
	public void testShouldHaveParameter() throws Exception {
		testErrorValidation(RequestMethod.POST, "/1/"+POST_SCORE.getId(), null, HttpStatusCode.BAD_REQUEST, ParamsFilter.WRONG_PARAMETERS);
	}
	
	@Test
	public void testShouldNotSupportOnlyParameterKey() throws Exception {
		testErrorValidation(RequestMethod.POST, "/1/"+POST_SCORE.getId()+"?key", null, HttpStatusCode.BAD_REQUEST, ParamsFilter.WRONG_PARAMETERS);
	}
	
	@Test
	public void testShouldNotSupportParameterKeyAndValues() throws Exception {
		testErrorValidation(RequestMethod.POST, "/1/"+POST_SCORE.getId()+"?key=value=value", null, HttpStatusCode.BAD_REQUEST, ParamsFilter.WRONG_PARAMETERS);
	}
	
	@Test
	public void testShouldHavePostStream() throws Exception {
		String parameter = POST_SCORE.getParameterNames().iterator().next();
		testErrorValidation(RequestMethod.POST, "/1/"+POST_SCORE.getId()+"?"+parameter+"=invalid", null, HttpStatusCode.BAD_REQUEST, ParamsFilter.WRONG_POST_STREAM);
	}
	
	@Test
	public void testShouldHaveIntegerPostStream() throws Exception {
		String parameter = POST_SCORE.getParameterNames().iterator().next();
		testErrorValidation(RequestMethod.POST, "/1/"+POST_SCORE.getId()+"?"+parameter+"=invalid", "invalid", HttpStatusCode.BAD_REQUEST, ParamsFilter.WRONG_POST_STREAM);
	}
	
	@Test
	public void testShouldNotHaveNegativePostStream() throws Exception {
		String parameter = POST_SCORE.getParameterNames().iterator().next();
		testErrorValidation(RequestMethod.POST, "/1/"+POST_SCORE.getId()+"?"+parameter+"=invalid", "-1", HttpStatusCode.BAD_REQUEST, ParamsFilter.WRONG_POST_STREAM);
	}
	
	@Test
	public void testShouldNotAuthorizedInvalidKey() throws Exception {
		String parameter = POST_SCORE.getParameterNames().iterator().next();
		testErrorValidation(RequestMethod.POST, "/1/"+POST_SCORE.getId()+"?"+parameter+"=invalid", "100", HttpStatusCode.NOT_AUTHORIZED, UsersServiceImpl.NOT_AUTHORIZED);
	}
	
	@Test
	public void testShouldPost() throws Exception {
		
		// Login 
		Integer uid = 9999;
		String sessionKey = testOkValidation(RequestMethod.GET, "/"+uid+"/"+RequestType.LOGIN.getId(), null);
		
		// Post score
		Integer level = 1;
		Integer score = 100;
		String parameter = POST_SCORE.getParameterNames().iterator().next();
		testNoResponseValidation(RequestMethod.POST, "/"+level+"/"+POST_SCORE.getId()+"?"+parameter+"="+sessionKey, score.toString());
		
		// Check
		Ranking ranking = RankingsDataImpl.getInstance().getRankingByLevel(level);
		Set<RankingElement> highest = ranking.getHighest(1);
		assertEquals("Should contain 1 element", highest.size(), 1);
		RankingElement element = ranking.getHighest(1).iterator().next();
		assertEquals("Should match", element.getUid().intValue(), uid.intValue());
		assertEquals("Should match", element.getScore().intValue(), score.intValue());
	}

}
