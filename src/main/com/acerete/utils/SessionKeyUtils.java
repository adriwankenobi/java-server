package com.acerete.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

public class SessionKeyUtils {

	// Thread safe
	private static final ThreadLocal<SecureRandom> RANDOM = new ThreadLocal<SecureRandom>() {
		@Override
		protected SecureRandom initialValue() {
			return new SecureRandom();
		}
	};
	
	/**
	 * Generates 'unique key' 
	 * @param uid
	 * @return session key
	 */
	public static String generateSessionKey() {
		return new BigInteger(130, RANDOM.get()).toString(32);
	}
}
