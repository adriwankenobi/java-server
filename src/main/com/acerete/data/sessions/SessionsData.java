package com.acerete.data.sessions;

import java.util.Map.Entry;
import java.util.Set;

import com.acerete.vo.Session;

public interface SessionsData {

	/**
	 * Gets session from key. Returns null if it doesn't exist
	 * @param sessionKey
	 * @return
	 */
	public Session getBySessionKey(String sessionKey);
	
	/**
	 * Gets all the sessions
	 * @return
	 */
	public Set<Entry<String, Session>> getAllSessions();
	
	/**
	 * Inserts a new session
	 * @param sessionKey
	 * @param sessionEntity
	 */
	public void insertSession(String sessionKey, Session session);
	
	/**
	 * Deletes the session from key
	 * @param sessionKey
	 */
	public void deleteBySessionKey(String sessionKey);
}
