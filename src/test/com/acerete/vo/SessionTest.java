package com.acerete.vo;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.acerete.test.SetupTest;

public class SessionTest extends SetupTest {
	
	@Test
	public void testShouldGetUid() {
		Session session = new Session(UID_TEST, DATE_TEST);
		Integer uid = session.getUid();
		assertEquals("Should match", uid.intValue(), UID_TEST.intValue());
	}
	
	@Test
	public void testShouldGetDate() {
		Session session = new Session(UID_TEST, DATE_TEST);
		Date date = session.getDate();
		assertEquals("Should match", date.getTime(), DATE_TEST.getTime());
	}

}
