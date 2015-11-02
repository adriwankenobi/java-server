package com.acerete.utils;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

import com.acerete.test.SetupTest;

public class DateUtilsTest extends SetupTest {
	
	@Test
	public void testShouldFormatDate() {
		String formattedDate = DateUtils.formatIso8601Date(LONG_TIME_AGO_TEST);
		assertEquals("Should match", formattedDate, LONG_TIME_AGO_FORMATTED_TEST);
	}

	@Test
	public void testShouldFormatString() throws ParseException {
		Date date = DateUtils.parseIso8601Date(LONG_TIME_AGO_FORMATTED_TEST);
		assertEquals("Should match", date, LONG_TIME_AGO_TEST);
	}
	
	@Test(expected = ParseException.class)
	public void testShouldNotFormatString() throws ParseException {
		DateUtils.parseIso8601Date(DATE_FORMATTED_INVALID_TEST);
	}
}
