package com.acerete.services.scores;

import com.acerete.httpserver.ClientConnection;
import com.acerete.services.Service;

public interface ScoresService extends Service {

	/**
	 * Handles posting user posting information about scores in level
	 * @param clientConnection
	 * @param postUserScoreToLevelRequest
	 */
	public void handlePostUserScoreToLevel(ClientConnection clientConnection,
			PostUserScoreToLevelServiceRequest postUserScoreToLevelRequest);
	
	/**
	 * Handles the retrieval of the leader board in a level
	 * @param clientConnection
	 * @param getHighScoreForLevelRequest
	 */
	public void handleGetHighScoreForLevel(ClientConnection clientConnection,
			GetHighScoreForLevelServiceRequest getHighScoreForLevelRequest);
}
