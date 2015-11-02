package com.acerete.services.message.response;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.acerete.test.SetupTest;

public class ResponseTypeTest extends SetupTest {

	@Test
	public void testShouldBeUniqueIds() {
		List<String> typeIds = new ArrayList<String>();
		for (ResponseType responseType : ResponseType.values()) {
			assertFalse("Should be unique", typeIds.contains(responseType.getId()));
			typeIds.add(responseType.getId());
		}
	}

}
