package com.acerete.utils;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.acerete.test.SetupTest;

public class SessionKeyUtilsTest extends SetupTest {

	@Test
	public void testShouldGenerateSessionKey() {
		assertNotNull("Should exist", SESSION_KEY_TEST);
		assertFalse("Should not be empty", SESSION_KEY_TEST.isEmpty());
	}
	
	@Test
	public void testShouldGenerate1000UniqueSessionKeys() {
		List<String> generatedKeys = new ArrayList<String>();
		for (int i=0; i<1000; i++) {
			String newKey = SessionKeyUtils.generateSessionKey();
			assertFalse("Should be unique", generatedKeys.contains(newKey));
			generatedKeys.add(newKey);
		}
	}
	
	@Test
	public void testShouldHaveSimpleCharacters() {
		for (int i=0; i<1000; i++) {
			String key = SessionKeyUtils.generateSessionKey();
			assertTrue("Should have simple characters", key.matches("[0-9a-z]+"));
		}
	}

}
