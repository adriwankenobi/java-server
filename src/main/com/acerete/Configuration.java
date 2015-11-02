package com.acerete;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.acerete.exceptions.LoadConfigurationFileException;

public class Configuration {
	
	// MESSAGE strings
	private final static String LOADING_FILE = "Loading configuration file ...";
	private final static String LOADED_FILE = "File loaded successfully!";
		
	// ERROR string
	private final static String ERROR_LOADING = "Error loading configuration file";
	
	// File location path
	private final static String CONF_FILE_NAME = "/com/acerete/configuration.properties";
	
	// Unit tests values
	private final static int TEST_MAX_VALID_MINUTES_FOR_VALID_SESSION = 1;
	private final static int TEST_MINUTES_TO_NEXT_SESSION_CLEAN = 1;
	
	// Thread safe lazy initialization singleton
	private static class LazyHolder {
        private static final Configuration INSTANCE = new Configuration();
    }
	
	public static Configuration getInstance() {
        return LazyHolder.INSTANCE;
    }
	
	private Properties properties;
	private boolean isUnitTest;
	
	private Configuration() {
		try {
			this.isUnitTest = false;
			System.out.println(LOADING_FILE);
			properties = new Properties();
			InputStream in = Properties.class.getResourceAsStream(CONF_FILE_NAME);
			properties.load(in);
			in.close();
			System.out.println(LOADED_FILE);
		} catch (IOException e) {
			throw new LoadConfigurationFileException(ERROR_LOADING, e);
		}
	}
	
	public void setUnitTestEnvironment() {
		this.isUnitTest = true;
	}
	
	public boolean isUnitTest() {
		return isUnitTest;
	}

	private String getProperty(String key) {
		return properties.getProperty(key);
	}
	
	private int getPropertyAsInt(String key) {
		return Integer.parseInt(getProperty(key));
	}
	
	private boolean getPropertyAsBoolean(String key) {
		return Boolean.parseBoolean(getProperty(key));
	}
	
	public String getServerHost() {
		return getProperty("server.host");
	}
	
	public int getServerPort() {
		return getPropertyAsInt("server.port");
	}
	
	public int getServerQueueSize() {
		return getPropertyAsInt("server.queue_size");
	}
	
	public int getServerCorePoolSize() {
		return getPropertyAsInt("server.core_pool_size");
	}
	
	public int getServerMaxPoolSize() {
		return getPropertyAsInt("server.max_pool_size");
	}
	
	public int getServerPosterQueueSize() {
		return getPropertyAsInt("server.poster.queue_size");
	}
	
	public int getServerPosterCorePoolSize() {
		return getPropertyAsInt("server.poster.core_pool_size");
	}
	
	public int getServerPosterMaxPoolSize() {
		return getPropertyAsInt("server.poster.max_pool_size");
	}
	
	public int getMaxValidMinutesForValidSession() {
		if (isUnitTest) {
			return TEST_MAX_VALID_MINUTES_FOR_VALID_SESSION;
		}
		else {
			return getPropertyAsInt("max_minutes_valid_session");
		}	
	}
	
	public int getMaxUsersHighScoresList() {
		return getPropertyAsInt("max_users_high_scores_list");
	}
	
	public int getMinutesToNextSessionClean() {
		if (isUnitTest) {
			return TEST_MINUTES_TO_NEXT_SESSION_CLEAN;
		}
		else {
			return getPropertyAsInt("minutes_to_next_session_clean");
		}	
	}
	
	public boolean sendErrorsToClient() {
		if (isUnitTest) {
			return true;
		}
		else {
			return getPropertyAsBoolean("send_error_messages_to_client");
		}
	}
}