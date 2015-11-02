package com.acerete.services.message.response;

import static org.junit.Assert.*;

import org.junit.Test;

import com.acerete.test.SetupTest;

public class ResponseTest extends SetupTest {

	@Test
	public void testShouldBeTextResponse() {
		Response response = new Response(RESPONSE_TYPE_TEST, RESPONSE_TEXT_TEST);
		assertEquals("Should match", response.getText(), RESPONSE_TEXT_TEST);
		assertEquals("Should match", response.getType().getId(), RESPONSE_TYPE_TEST.getId());
	}

}
