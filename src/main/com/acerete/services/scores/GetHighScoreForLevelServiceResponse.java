package com.acerete.services.scores;

import java.util.SortedSet;

import com.acerete.services.message.response.Response;
import com.acerete.services.message.response.ResponseType;
import com.acerete.services.message.response.ServiceResponse;
import com.acerete.utils.SetUtils;
import com.acerete.vo.RankingElement;

public class GetHighScoreForLevelServiceResponse implements ServiceResponse {
	
	SortedSet<RankingElement> highScores;
	
	public GetHighScoreForLevelServiceResponse(SortedSet<RankingElement> highScores) {
		this.highScores = highScores;
	}

	@Override
	public Response getResponse() {
		// Response as CSV file named 'highscorelist.csv'
		return new Response(ResponseType.GET_HIGH_SCORE_LIST_FOR_LEVEL, 
							SetUtils.getSetAsString(highScores));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GetHighScoreForLevelResponse [highScores=").append(highScores).append("]");
		return builder.toString();
	}

}
