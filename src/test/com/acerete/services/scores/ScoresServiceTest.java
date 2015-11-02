package com.acerete.services.scores;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import com.acerete.test.SetupTest;
import com.acerete.vo.RankingElement;

public class ScoresServiceTest extends SetupTest {

	@Test
	public void testShouldNotGetScoreInRanking() {
		Set<RankingElement> highest = ScoresServiceImpl.getInstance().getHighScores(LEVEL_TEST);
		assertTrue("Should match size", highest.isEmpty());
	}
	
	@Test
	public void testShouldPostAndGetScoreInRanking() {
		ScoresServiceImpl.getInstance().postScoreInRanking(UID_TEST, LEVEL_TEST, SCORE_TEST);
		Set<RankingElement> highest = ScoresServiceImpl.getInstance().getHighScores(LEVEL_TEST);
		assertEquals("Should match size", highest.size(), 1);
		RankingElement element = highest.iterator().next();
		assertEquals("Should match", element.getUid().intValue(), UID_TEST.intValue());
		assertEquals("Should match", element.getScore().intValue(), SCORE_TEST.intValue());
	}
	
	@Test
	public void testShouldReplaceAndGetScoreInRanking() {
		ScoresServiceImpl.getInstance().postScoreInRanking(UID_TEST, LEVEL_TEST, SCORE_TEST);
		ScoresServiceImpl.getInstance().postScoreInRanking(UID_TEST, LEVEL_TEST, NEW_SCORE_TEST);
		Set<RankingElement> highest = ScoresServiceImpl.getInstance().getHighScores(LEVEL_TEST);
		assertEquals("Should match size", highest.size(), 1);
		RankingElement element = highest.iterator().next();
		assertEquals("Should match", element.getUid().intValue(), UID_TEST.intValue());
		assertEquals("Should match", element.getScore().intValue(), NEW_SCORE_TEST.intValue());
	}

	@Test
	public void testShouldNotReplaceAndGetScoreInRanking() {
		ScoresServiceImpl.getInstance().postScoreInRanking(UID_TEST, LEVEL_TEST, SCORE_TEST);
		ScoresServiceImpl.getInstance().postScoreInRanking(UID_TEST, LEVEL_TEST, LOWER_SCORE_TEST);
		Set<RankingElement> highest = ScoresServiceImpl.getInstance().getHighScores(LEVEL_TEST);
		assertEquals("Should match size", highest.size(), 1);
		RankingElement element = highest.iterator().next();
		assertEquals("Should match", element.getUid().intValue(), UID_TEST.intValue());
		assertEquals("Should match", element.getScore().intValue(), SCORE_TEST.intValue());
	}

	@Test
	public void testShouldPostAndGetScoreInRankingSeveral() {
		int offset = 5;
		for (int i=0; i<NUMBER_OF_ELEMENTS_IN_RANKING_TEST+offset; i++) {
			ScoresServiceImpl.getInstance().postScoreInRanking(i, LEVEL_TEST, SCORE_TEST+i);
		}
		Set<RankingElement> highest = ScoresServiceImpl.getInstance().getHighScores(LEVEL_TEST);
		assertEquals("Should match size", highest.size(), NUMBER_OF_ELEMENTS_IN_RANKING_TEST);
		Iterator<RankingElement> iterator = highest.iterator();
		for (int i=NUMBER_OF_ELEMENTS_IN_RANKING_TEST+offset-1; i>=offset; i--) {
			RankingElement element = iterator.next();
			assertEquals("Should match", element.getUid().intValue(), i);
			assertEquals("Should match", element.getScore().intValue(), SCORE_TEST+i);
		}
		
	}
	
}
