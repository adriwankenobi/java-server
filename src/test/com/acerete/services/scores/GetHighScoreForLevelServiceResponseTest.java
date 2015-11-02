package com.acerete.services.scores;

import static org.junit.Assert.*;

import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

import com.acerete.services.message.response.Response;
import com.acerete.services.message.response.ResponseType;
import com.acerete.test.SetupTest;
import com.acerete.vo.RankingElement;

public class GetHighScoreForLevelServiceResponseTest extends SetupTest {

	@Test
	public void testShouldGetExceptionServiceResponse() {
		SortedSet<RankingElement> highScores = new TreeSet<RankingElement>();
		highScores.add(new RankingElement(1, 100));
		highScores.add(new RankingElement(2, 200));
		highScores.add(new RankingElement(3, 300));
		GetHighScoreForLevelServiceResponse service = new GetHighScoreForLevelServiceResponse(highScores);
		Response response = service.getResponse();
		assertEquals("Should match", response.getType().getId(), ResponseType.GET_HIGH_SCORE_LIST_FOR_LEVEL.getId());
		assertEquals("Should get file content", response.getText(), "3=300,2=200,1=100");
	}

}
