package com.acerete.data.sessions;

import com.acerete.Configuration;
import com.acerete.exceptions.CannotBeExecutedException;
import com.acerete.vo.Session;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SessionsDataImpl implements SessionsData {

	// ERROR strings
	private final static String ERROR_NO_UNIT_TEST = "This method cannot be executed outside unit tests";
	
	// Thread safe lazy initialization singleton
	private static class LazyHolder {
		private static final SessionsDataImpl INSTANCE = new SessionsDataImpl();
	}
	
	public static SessionsDataImpl getInstance() {
		return LazyHolder.INSTANCE;
	}
		
	private ConcurrentHashMap<String, Session> data;
	
	private SessionsDataImpl() {
		this.data = new ConcurrentHashMap<String, Session>();
	}
	
	@Override
	public Session getBySessionKey(String sessionKey) {
		return data.get(sessionKey);
	}
	
	@Override
	public Set<Entry<String, Session>> getAllSessions() {
		return data.entrySet();
	}
	
	@Override
	public void insertSession(String sessionKey, Session session) {
		data.put(sessionKey, session);
	}
	
	@Override
	public void deleteBySessionKey(String sessionKey) {
		data.remove(sessionKey);
	}
	
	public void clearAllSessions() {
		if (Configuration.getInstance().isUnitTest()) {
			for (String sessionKey : data.keySet()) {
				data.remove(sessionKey);
			}
		}
		else {
			throw new CannotBeExecutedException(ERROR_NO_UNIT_TEST);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Sessions [").append(data).append("]");
		return builder.toString();
	}
}
