package com.acerete.test;

import java.util.Date;

import org.junit.After;
import org.junit.Before;

import com.acerete.Configuration;
import com.acerete.data.ranking.RankingsDataImpl;
import com.acerete.data.sessions.SessionsDataImpl;
import com.acerete.services.message.request.RequestType;
import com.acerete.services.message.response.ResponseType;
import com.acerete.utils.SessionKeyUtils;
import com.acerete.vo.Ranking;
import com.acerete.vo.RankingElement;
import com.acerete.vo.Session;

public abstract class SetupTest {
	
	protected final static Date LONG_TIME_AGO_TEST = new Date(0);
	protected final static String LONG_TIME_AGO_FORMATTED_TEST = "1970-01-01T00:00:00+0000";
	protected final static String DATE_FORMATTED_INVALID_TEST = "dateFormatedInvalidTest";
	
	protected final static Integer UID_TEST = 9999999;
	protected final static Date DATE_TEST = new Date();
	protected final static Integer LEVEL_TEST = 1;
	
	protected final static Integer SCORE_TEST = 100;
	protected final static Integer NEW_SCORE_TEST = 200;
	protected final static Integer LOWER_SCORE_TEST = 50;
	
	protected final static String SESSION_KEY_INVALID_TEST = "sadfsadf";
	protected final static String SESSION_KEY_TEST = SessionKeyUtils.generateSessionKey();
	
	protected final static Session SESSION_TEST = new Session(UID_TEST, DATE_TEST);
	protected final static Ranking RANKING_TEST = new Ranking();
	
	protected final static RankingElement RANKING_ELEMENT_TEST = new RankingElement(UID_TEST, SCORE_TEST);
	protected final static RankingElement NEW_RANKING_ELEMENT_TEST = new RankingElement(UID_TEST, NEW_SCORE_TEST);
	protected final static RankingElement LOWER_RANKING_ELEMENT_TEST = new RankingElement(UID_TEST, LOWER_SCORE_TEST);
	
	protected final static int NUMBER_OF_ELEMENTS_IN_RANKING_TEST = Configuration.getInstance().getMaxUsersHighScoresList();
	
	protected final static RequestType REQUEST_TYPE_TEST = RequestType.LOGIN;
	protected final static String REQUEST_ERROR_TEST = "requestErrorTest";
	protected final static String REQUEST_PARAMETER_NAME_TEST = "requestParameterNameTest";
	protected final static String REQUEST_PARAMETER_VALUE_TEST = "requestParameterValueTest";
	protected final static String REQUEST_TYPE_INVALID_TEST = "requestTypeInvalidTest";
	
	protected final static ResponseType RESPONSE_TYPE_TEST = ResponseType.LOGIN;
	protected final static String RESPONSE_TEXT_TEST = "responseTest";
	protected final static String RESPONSE_FILENAME_TEST = "filenameTest";
	
	static {
		Configuration.getInstance().setUnitTestEnvironment();
	}
	
	@Before
	public void before() {
		SessionsDataImpl.getInstance().clearAllSessions();
		RankingsDataImpl.getInstance().clearAllRankings();
	}

	@After
	public void after() {
		
	}
}
