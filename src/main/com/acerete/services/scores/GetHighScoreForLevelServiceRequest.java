package com.acerete.services.scores;

import com.acerete.httpserver.ClientConnection;
import com.acerete.services.message.request.ServiceRequest;

public class GetHighScoreForLevelServiceRequest implements ServiceRequest {

	private final Integer levelId;
	
	public GetHighScoreForLevelServiceRequest(Integer levelId) {
		this.levelId = levelId;
	}
	
	public Integer getLevelId() {
		return levelId;
	}
	
	@Override
	public void handle(ClientConnection clientConnection) {
		ScoresServiceImpl.getInstance().handleGetHighScoreForLevel(clientConnection, this);
	}
 
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GetHighScoreForLevelRequest [levelId=").append(levelId).append("]");
		return builder.toString();
	}
}