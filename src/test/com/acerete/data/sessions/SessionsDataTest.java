package com.acerete.data.sessions;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.junit.Test;

import com.acerete.test.SetupTest;
import com.acerete.vo.Session;

public class SessionsDataTest extends SetupTest {
	
	@Test
	public void testShouldNotGetSession() {
		Session session = SessionsDataImpl.getInstance().getBySessionKey(SESSION_KEY_TEST);
		assertNull("Should be null", session);
	}

	@Test
	public void testShouldPutAndGetSession() {
		SessionsDataImpl.getInstance().insertSession(SESSION_KEY_TEST, SESSION_TEST);
		Session session = SessionsDataImpl.getInstance().getBySessionKey(SESSION_KEY_TEST);
		assertNotNull("Should not be null", session);
		assertEquals("Should match", session.getUid(), UID_TEST);
		assertEquals("Should match", session.getDate().getTime(), DATE_TEST.getTime());
	}
	
	@Test
	public void testShouldNotGetAllSessions() {
		Set<Entry<String, Session>> allSessions = SessionsDataImpl.getInstance().getAllSessions();
		assertNotNull("Should not be null", allSessions);
		assertTrue("Should be empty", allSessions.isEmpty());
	}
	
	@Test
	public void testShouldGetAllSessions() {
		int noOfSessions = 5;
		Map<String, Session> initials = new HashMap<String,Session>();
		for (int i=0; i<noOfSessions; i++) {
			String sessionKey = SESSION_KEY_TEST+i;
			Session session = new Session(UID_TEST+i, DATE_TEST);
			initials.put(sessionKey, session);
			SessionsDataImpl.getInstance().insertSession(sessionKey, session);
		}
		Set<Entry<String, Session>> allSessions = SessionsDataImpl.getInstance().getAllSessions();
		assertEquals("Should match size", allSessions.size(), noOfSessions);
		for (Entry<String, Session> session : allSessions) {
			assertTrue("Should key be contained", initials.containsKey(session.getKey()));
			assertTrue("Should value be contained", initials.containsValue(session.getValue()));
		}
	}
	
	@Test
	public void testShouldNotDeleteSession() {
		SessionsDataImpl.getInstance().deleteBySessionKey(SESSION_KEY_TEST);
		Set<Entry<String, Session>> allSessions = SessionsDataImpl.getInstance().getAllSessions();
		assertNotNull("Should not be null", allSessions);
		assertTrue("Should be empty", allSessions.isEmpty());
	}
	
	@Test
	public void testShouldDeleteSession() {
		SessionsDataImpl.getInstance().insertSession(SESSION_KEY_TEST, SESSION_TEST);
		Session session = SessionsDataImpl.getInstance().getBySessionKey(SESSION_KEY_TEST);
		assertNotNull("Should not be null", session);
		assertEquals("Should match", session.getUid(), UID_TEST);
		assertEquals("Should match", session.getDate().getTime(), DATE_TEST.getTime());
		SessionsDataImpl.getInstance().deleteBySessionKey(SESSION_KEY_TEST);
		session = SessionsDataImpl.getInstance().getBySessionKey(SESSION_KEY_TEST);
		assertNull("Should be null", session);
	}
}
