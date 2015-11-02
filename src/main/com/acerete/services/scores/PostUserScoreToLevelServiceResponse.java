package com.acerete.services.scores;

import com.acerete.services.message.response.Response;
import com.acerete.services.message.response.ResponseType;
import com.acerete.services.message.response.ServiceResponse;

public class PostUserScoreToLevelServiceResponse implements ServiceResponse {
	
	public PostUserScoreToLevelServiceResponse() {
		
	}

	@Override
	public Response getResponse() {
		return new Response(ResponseType.POST_USER_SCORE_TO_LEVEL, null);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PostUserScoreToLevelResponse []");
		return builder.toString();
	}

}
