package com.acerete.utils;

import static org.junit.Assert.*;

import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

import com.acerete.test.SetupTest;
import com.acerete.vo.RankingElement;

public class SetUtilsTest extends SetupTest {

	@Test
	public void testShouldNotGetNullMap() {
		SortedSet<RankingElement> set = null;
		String formated = SetUtils.getSetAsString(set);
		assertEquals("Should match", formated, "");
	}
	
	@Test
	public void testShouldNotGetEmptyMap() {
		SortedSet<RankingElement> set = new TreeSet<RankingElement>();
		String formated = SetUtils.getSetAsString(set);
		assertEquals("Should match", formated, "");
	}
	
	@Test
	public void testShouldGetSetAsString() {
		SortedSet<RankingElement> set = new TreeSet<RankingElement>();
		set.add(new RankingElement(1, 100));
		set.add(new RankingElement(3, 300));
		set.add(new RankingElement(2, 200));
		String formated = SetUtils.getSetAsString(set);
		assertEquals("Should match", formated, "3=300,2=200,1=100");
	}

}
