package com.acerete.services.users;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.acerete.Configuration;
import com.acerete.exceptions.NotAuthorizedUserException;
import com.acerete.test.SetupTest;
import com.acerete.vo.Session;

public class UsersServiceTest extends SetupTest {
	
	@Test
	public void testShouldBeValidSession() {
		assertTrue("Should be valid", UsersServiceImpl.getInstance().isSessionValid(SESSION_TEST));
	}
	
	@Test
	public void testShouldNotBeValidSession() {
		// Simulate old date
		int minutesValidSession = Configuration.getInstance().getMaxValidMinutesForValidSession();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, -minutesValidSession-5);
		Date oldDate = new Date(calendar.getTimeInMillis());
				
		Session session = new Session(UID_TEST, oldDate);
		assertFalse("Should not be valid", UsersServiceImpl.getInstance().isSessionValid(session));
	}
	
	@Test(expected = NotAuthorizedUserException.class)
	public void testShouldBeNotAuthorizedSessionKey() {
		UsersServiceImpl.getInstance().getUidBySessionKey(SESSION_KEY_INVALID_TEST);
	}
	
	@Test
	public void testShouldDoLoginAndGetUser() {
		String sessionKey = UsersServiceImpl.getInstance().doLogin(UID_TEST);
		Integer uid = UsersServiceImpl.getInstance().getUidBySessionKey(sessionKey);
		assertEquals("Should match", uid.intValue(), UID_TEST.intValue());
	}
	
}
