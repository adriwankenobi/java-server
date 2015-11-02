package com.acerete.vo;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Random;
import java.util.SortedSet;

import org.junit.Test;

import com.acerete.test.SetupTest;

public class RankingTest extends SetupTest {

	@Test
	public void testShouldNotGetHighElement() {
		Ranking ranking = new Ranking();
		SortedSet<RankingElement> highest = ranking.getHighest(NUMBER_OF_ELEMENTS_IN_RANKING_TEST);
		assertNotNull("Should not be null", highest);
		assertTrue("Should not have users", highest.isEmpty());
	}
	
	@Test
	public void testShouldAddAndGetHighElementsOneUser() {
		Ranking ranking = new Ranking();
		ranking.addOrReplace(RANKING_ELEMENT_TEST);
		SortedSet<RankingElement> highest = ranking.getHighest(NUMBER_OF_ELEMENTS_IN_RANKING_TEST);
		assertNotNull("Should not be null", highest);
		assertEquals("Should have one user", highest.size(), 1);
		RankingElement element = highest.first();
		assertEquals("Should match user", element.getUid(), UID_TEST);
		assertEquals("Should match score", element.getScore(), SCORE_TEST);
	}
	
	@Test
	public void testShouldReplaceAndGetHighElementsOneUser() {
		Ranking ranking = new Ranking();
		ranking.addOrReplace(RANKING_ELEMENT_TEST);
		ranking.addOrReplace(NEW_RANKING_ELEMENT_TEST);
		SortedSet<RankingElement> highest = ranking.getHighest(NUMBER_OF_ELEMENTS_IN_RANKING_TEST);
		assertNotNull("Should not be null", highest);
		assertEquals("Should have one user", highest.size(), 1);
		RankingElement element = highest.first();
		assertEquals("Should match user", element.getUid(), UID_TEST);
		assertEquals("Should match score", element.getScore(), NEW_SCORE_TEST);
	}
	
	@Test
	public void testShouldNotReplaceAndGetHighElementsOneUser() {
		Ranking ranking = new Ranking();
		ranking.addOrReplace(RANKING_ELEMENT_TEST);
		ranking.addOrReplace(LOWER_RANKING_ELEMENT_TEST);
		SortedSet<RankingElement> highest = ranking.getHighest(NUMBER_OF_ELEMENTS_IN_RANKING_TEST);
		assertNotNull("Should not be null", highest);
		assertEquals("Should have one user", highest.size(), 1);
		RankingElement element = highest.first();
		assertEquals("Should match user", element.getUid(), UID_TEST);
		assertEquals("Should match score", element.getScore(), SCORE_TEST);
	}
	
	@Test
	public void testShouldGetHighScoresMaxSize() {
		Ranking ranking = new Ranking();
		for (int i=0; i<NUMBER_OF_ELEMENTS_IN_RANKING_TEST+5; i++) {
			ranking.addOrReplace(new RankingElement(i, SCORE_TEST));
		}
		SortedSet<RankingElement> highest = ranking.getHighest(NUMBER_OF_ELEMENTS_IN_RANKING_TEST);
		assertEquals("Should contain MAX elements", highest.size(), NUMBER_OF_ELEMENTS_IN_RANKING_TEST);
	}
	
	@Test
	public void testShouldInsertInSortedPosition() {
		Ranking ranking = new Ranking();
		for (int i=0; i<NUMBER_OF_ELEMENTS_IN_RANKING_TEST; i++) {
			ranking.addOrReplace(new RankingElement(i, SCORE_TEST+i));
		}
		int offset = 5;
		ranking.addOrReplace(new RankingElement(UID_TEST, SCORE_TEST + offset));
		SortedSet<RankingElement> highest = ranking.getHighest(NUMBER_OF_ELEMENTS_IN_RANKING_TEST);
		Iterator<RankingElement> iterator = highest.iterator();
		for (int i=(NUMBER_OF_ELEMENTS_IN_RANKING_TEST-1); i>=offset; i--) {
			RankingElement j = iterator.next();
			assertEquals("Should match user", j.getUid().intValue(), i);
			assertEquals("Should match score", j.getScore().intValue(), SCORE_TEST+i);
		}
		RankingElement element = iterator.next();
		assertEquals("Should match user", element.getUid().intValue(), UID_TEST.intValue());
		assertEquals("Should match score", element.getScore().intValue(), SCORE_TEST + offset);
		for (int i=offset; i>1; i--) {
			RankingElement j = iterator.next();
			assertEquals("Should match user", j.getUid().intValue(), i-1);
			assertEquals("Should match score", j.getScore().intValue(), SCORE_TEST+i-1);
		}
	}
	
	@Test
	public void testShouldGetSortedList() {
		Ranking ranking = new Ranking();
		Random randomGenerator = new Random();
		for (int i=0; i<NUMBER_OF_ELEMENTS_IN_RANKING_TEST; i++) {
			ranking.addOrReplace(new RankingElement(i, randomGenerator.nextInt(100)));
		}
		SortedSet<RankingElement> highest = ranking.getHighest(NUMBER_OF_ELEMENTS_IN_RANKING_TEST);
		Iterator<RankingElement> iterator = highest.iterator();
		Integer previousScore = Integer.MAX_VALUE;
		while (iterator.hasNext()) {
			RankingElement element = iterator.next();
			assertTrue("Should be lower than previous", element.getScore().intValue() <= previousScore.intValue());
			previousScore = element.getScore();
		}
	}

}
