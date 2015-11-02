package com.acerete.services.message.request;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.acerete.exceptions.RequestNotFoundException;
import com.acerete.test.SetupTest;

public class RequestTypeTest extends SetupTest {

	@Test
	public void testShouldBeUniqueIds() {
		List<String> typeIds = new ArrayList<String>();
		for (RequestType requestType : RequestType.values()) {
			assertFalse("Should be unique", typeIds.contains(requestType.getId()));
			typeIds.add(requestType.getId());
		}
	}

	@Test
	public void testShouldGetByIds() {
		for (RequestType requestType : RequestType.values()) {
			RequestType result = RequestType.getById(requestType.getId());
			assertEquals("Should match", result.getId(), requestType.getId());
		}
	}
	
	@Test(expected = RequestNotFoundException.class)
	public void testShouldNotGetByIds() {
		RequestType.getById(REQUEST_TYPE_INVALID_TEST);
	}
}
