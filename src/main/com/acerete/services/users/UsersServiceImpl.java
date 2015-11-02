package com.acerete.services.users;

import java.util.Date;
import java.util.Map.Entry;
import java.util.Set;

import com.acerete.Configuration;
import com.acerete.data.sessions.SessionsDataImpl;
import com.acerete.exceptions.NotAuthorizedUserException;
import com.acerete.exceptions.ServicesException;
import com.acerete.httpserver.ClientConnection;
import com.acerete.utils.SessionKeyUtils;
import com.acerete.vo.Session;

public class UsersServiceImpl implements UsersService {
	
	// ERROR strings
	private final static String ERROR_HANDLE_LOGIN = "Error occured in handleLogin: ";
	public final static String NOT_AUTHORIZED = "Not authorized user";
		
	// Thread safe lazy initialization singleton
	private static class LazyHolder {
		private static final UsersServiceImpl INSTANCE = new UsersServiceImpl();
	}
	
	public static UsersServiceImpl getInstance() {
        return LazyHolder.INSTANCE;
    }
	
	// Thread to clean old sessions
	private class SessionCleaner extends Thread {

		public SessionCleaner() {
		}
		
		@Override
		public void run() {
			System.out.println("SessionCleaner: start");
			try {
				int minutesToNextClean = Configuration.getInstance().getMinutesToNextSessionClean();
				while (true) {
					System.out.println("SessionCleaner: fetching sessions ...");
					Set<Entry<String, Session>> sessions = SessionsDataImpl.getInstance().getAllSessions();
					for (Entry<String, Session> session : sessions) {
						if (!isSessionValid(session.getValue())) {
							SessionsDataImpl.getInstance().deleteBySessionKey(session.getKey());
						}
					}
					System.out.println("SessionCleaner: about to sleep for " + minutesToNextClean + " min");
					Thread.sleep(minutesToNextClean * 60 * 1000);
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	private UsersServiceImpl() {
		SessionCleaner sessionCleaner = new SessionCleaner();
		sessionCleaner.setDaemon(true);
		sessionCleaner.start();
	}
	
	@Override
	public void handleLogin(ClientConnection clientConnection, LoginServiceRequest loginRequest) {
			
		try {
				
			Integer uid = loginRequest.getUserId();
		
			String sessionKey = doLogin(uid);
				
			LoginServiceResponse loginResponse = new LoginServiceResponse(sessionKey);
			clientConnection.sendResponse(loginResponse);
				
		} catch (Exception e) {
			throw new ServicesException(ERROR_HANDLE_LOGIN + e.getMessage(), e);
		}
	}

	@Override
	public Integer getUidBySessionKey(String sessionKey) throws NotAuthorizedUserException {
		Session session = SessionsDataImpl.getInstance().getBySessionKey(sessionKey);
		if (session != null && isSessionValid(session)) {
			return session.getUid();
		}
		else {
			throw new NotAuthorizedUserException(NOT_AUTHORIZED);
		}
	}
	
	protected String doLogin(Integer uid) {
		Session session = new Session(uid, new Date());
		String sessionKey = SessionKeyUtils.generateSessionKey();
		SessionsDataImpl.getInstance().insertSession(sessionKey, session);
		return sessionKey;
	}
	
	protected boolean isSessionValid(Session session) {
		Date now = new Date();
		long diffInMillis = now.getTime() - session.getDate().getTime();
		float diffInMinutes = diffInMillis / (1000 * 60);
		return diffInMinutes <= Configuration.getInstance().getMaxValidMinutesForValidSession();
	}
}