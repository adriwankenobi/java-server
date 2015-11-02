package com.acerete.services.scores;

import java.util.SortedSet;

import com.acerete.Configuration;
import com.acerete.data.ranking.RankingsDataImpl;
import com.acerete.exceptions.ServicesException;
import com.acerete.httpserver.ClientConnection;
import com.acerete.vo.Ranking;
import com.acerete.vo.RankingElement;

public class ScoresServiceImpl implements ScoresService {

	// ERROR strings
	private final static String ERROR_HANDLE_POST_USER_SCORE_TO_LEVEL = "Error occured in handlePostUserScoreToLevel: ";
	private final static String ERROR_HANDLE_GET_HIGH_SCORE_FOR_LEVEL = "Error occured in handleGetHighScoreForLevel: ";
		
	// Thread safe lazy initialization singleton
	private static class LazyHolder {
		private static final ScoresServiceImpl INSTANCE = new ScoresServiceImpl();
	}
	
	public static ScoresServiceImpl getInstance() {
		return LazyHolder.INSTANCE;
	}
	
	private Object createLevelLock;
	private PostScoreInRankingExecutor scoreExecutor;
	
	public class PostScoreInRankingRunnable implements Runnable {
		
	    private Integer uid;
	    private PostUserScoreToLevelServiceRequest postUserScoreToLevelRequest;
	 
	    public PostScoreInRankingRunnable(Integer uid, PostUserScoreToLevelServiceRequest postUserScoreToLevelRequest) {
	        this.uid = uid;
	        this.postUserScoreToLevelRequest = postUserScoreToLevelRequest;
	    }
	 
	    @Override
	    public void run() {
	    	ScoresServiceImpl.getInstance().postScoreInRanking(uid, postUserScoreToLevelRequest.getLevelId(), postUserScoreToLevelRequest.getScore());
	    }
	    
	}

	private ScoresServiceImpl() {
		this.createLevelLock = new Object();
		this.scoreExecutor = new PostScoreInRankingExecutor();
	}
	
	@Override
	public void handlePostUserScoreToLevel(ClientConnection clientConnection,
			PostUserScoreToLevelServiceRequest postUserScoreToLevelRequest) {
		
		try {
			
			Integer uid = clientConnection.getUid();
		
			scoreExecutor.execute(new PostScoreInRankingRunnable(uid, postUserScoreToLevelRequest));
			
			PostUserScoreToLevelServiceResponse postUserScoreToLevelResponse = new PostUserScoreToLevelServiceResponse();
			clientConnection.sendResponse(postUserScoreToLevelResponse);
			
		} catch (Exception e) {
			throw new ServicesException(ERROR_HANDLE_POST_USER_SCORE_TO_LEVEL + e.getMessage(), e);
		}
	}

	@Override
	public void handleGetHighScoreForLevel(ClientConnection clientConnection,
			GetHighScoreForLevelServiceRequest getHighScoreForLevelRequest) {
		
		try {
			
			Integer levelId = getHighScoreForLevelRequest.getLevelId();
		
			SortedSet<RankingElement> highScores = getHighScores(levelId);
			
			GetHighScoreForLevelServiceResponse getHighScoreForLevelResponse = new GetHighScoreForLevelServiceResponse(highScores);
			clientConnection.sendResponse(getHighScoreForLevelResponse);
			
		} catch (Exception e) {
			throw new ServicesException(ERROR_HANDLE_GET_HIGH_SCORE_FOR_LEVEL + e.getMessage(), e);
		}
	}

	protected void postScoreInRanking(Integer uid, Integer levelId, Integer score) {
		Ranking ranking = RankingsDataImpl.getInstance().getRankingByLevel(levelId);
		if (ranking == null) {
			synchronized (createLevelLock) {
				ranking = RankingsDataImpl.getInstance().getRankingByLevel(levelId);
				if (ranking == null) {
					ranking = new Ranking();
					RankingsDataImpl.getInstance().insertOrUpdateRanking(levelId, ranking);
				}
			}
		}
		Object levelLock = ranking.getLock();
		synchronized (levelLock) {
			ranking = RankingsDataImpl.getInstance().getRankingByLevel(levelId);
			ranking.addOrReplace(new RankingElement(uid, score));
			RankingsDataImpl.getInstance().insertOrUpdateRanking(levelId, ranking);
		}
	}
	
	protected SortedSet<RankingElement> getHighScores(Integer levelId) {
		Ranking ranking = RankingsDataImpl.getInstance().getRankingByLevel(levelId);
		if (ranking == null) {
			ranking = new Ranking();
		}
		return ranking.getHighest(Configuration.getInstance().getMaxUsersHighScoresList());
	}

}
