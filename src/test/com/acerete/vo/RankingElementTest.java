package com.acerete.vo;

import static org.junit.Assert.*;

import org.junit.Test;

import com.acerete.test.SetupTest;

public class RankingElementTest extends SetupTest {
	
	@Test
	public void testShouldGetUid() {
		RankingElement element = new RankingElement(UID_TEST, SCORE_TEST);
		Integer uid = element.getUid();
		assertEquals("Should match", uid.intValue(), UID_TEST.intValue());
	}
	
	@Test
	public void testShouldGetScore() {
		RankingElement element = new RankingElement(UID_TEST, SCORE_TEST);
		Integer score = element.getScore();
		assertEquals("Should match", score.intValue(), SCORE_TEST.intValue());
	}

}
