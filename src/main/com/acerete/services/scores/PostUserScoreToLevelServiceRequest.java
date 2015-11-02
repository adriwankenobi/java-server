package com.acerete.services.scores;

import com.acerete.httpserver.ClientConnection;
import com.acerete.services.message.SecureMessage;
import com.acerete.services.message.request.ServiceRequest;

public class PostUserScoreToLevelServiceRequest implements ServiceRequest, SecureMessage {
	
	private final String sessionKey;
	private final Integer levelId;
	private final Integer score;
	
	public PostUserScoreToLevelServiceRequest(String sessionKey, Integer levelId, Integer score) {
		this.sessionKey = sessionKey;
		this.levelId = levelId;
		this.score = score;
	}
	
	public Integer getLevelId() {
		return levelId;
	}
	
	public Integer getScore() {
		return score;
	}

	@Override
	public String getSessionKey() {
		return sessionKey;
	}

	@Override
	public void handle(ClientConnection clientConnection) {
		ScoresServiceImpl.getInstance().handlePostUserScoreToLevel(clientConnection, this);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PostUserScoreToLevelRequest [sessionKey=").append(sessionKey)
			.append(", levelId=").append(levelId).append(", score=").append(score).append("]");
		return builder.toString();
	}
 
}