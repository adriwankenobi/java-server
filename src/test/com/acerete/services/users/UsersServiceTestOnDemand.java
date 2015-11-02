package com.acerete.services.users;

import static org.junit.Assert.*;

import com.acerete.Configuration;
import com.acerete.data.sessions.SessionsDataImpl;
import com.acerete.test.SetupTest;
import com.acerete.vo.Session;

public class UsersServiceTestOnDemand extends SetupTest {

	/**
	 * On demand tests. They include Thread.sleep command that takes some time
	 */
	
	//@Test //test on demand
	public void testShouldStopBeingValidSession() throws InterruptedException {
		assertTrue("Should be valid", UsersServiceImpl.getInstance().isSessionValid(SESSION_TEST));
		int minutesValidSession = Configuration.getInstance().getMaxValidMinutesForValidSession();
		Thread.sleep((minutesValidSession + 1) * 60 * 1000);
		assertFalse("Should not be valid", UsersServiceImpl.getInstance().isSessionValid(SESSION_TEST));
	}
	
	//@Test //test on demand
	public void testShouldCleanOldSessions() throws InterruptedException {
		String sessionKey = UsersServiceImpl.getInstance().doLogin(UID_TEST);
		Session session = SessionsDataImpl.getInstance().getBySessionKey(sessionKey);
		assertNotNull("Should exist", session);
		int minutesToNextClean = Configuration.getInstance().getMinutesToNextSessionClean();
		Thread.sleep((minutesToNextClean + 1) * 60 * 1000);
		session = SessionsDataImpl.getInstance().getBySessionKey(sessionKey);
		assertNull("Should not exist", session);
	}

}
