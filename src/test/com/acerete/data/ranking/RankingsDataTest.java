package com.acerete.data.ranking;

import static org.junit.Assert.*;

import org.junit.Test;

import com.acerete.test.SetupTest;
import com.acerete.vo.Ranking;

public class RankingsDataTest extends SetupTest {
	
	@Test
	public void testShouldNotGetRanking() {
		Ranking ranking = RankingsDataImpl.getInstance().getRankingByLevel(LEVEL_TEST);
		assertNull("Should be null", ranking);
	}
	
	@Test
	public void testShouldPutAndGetRanking() {
		RankingsDataImpl.getInstance().insertOrUpdateRanking(LEVEL_TEST, RANKING_TEST);
		Ranking ranking = RankingsDataImpl.getInstance().getRankingByLevel(LEVEL_TEST);
		assertNotNull("Should not be null", ranking);
		assertEquals("Should match", ranking, RANKING_TEST);
	}
	
	@Test
	public void testShouldReplaceAndGetRanking() {
		RankingsDataImpl.getInstance().insertOrUpdateRanking(LEVEL_TEST, RANKING_TEST);
		Ranking ranking = RankingsDataImpl.getInstance().getRankingByLevel(LEVEL_TEST);
		Ranking newRanking = new Ranking();
		newRanking.addOrReplace(RANKING_ELEMENT_TEST);
		RankingsDataImpl.getInstance().insertOrUpdateRanking(LEVEL_TEST, newRanking);
		ranking = RankingsDataImpl.getInstance().getRankingByLevel(LEVEL_TEST);
		assertNotNull("Should not be null", ranking);
		assertEquals("Should match", ranking, newRanking);
	}

}
