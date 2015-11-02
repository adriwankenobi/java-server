package com.acerete.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.acerete.Configuration;
import com.acerete.httpserver.CustomHttpServer;
import com.acerete.httpserver.CustomHttpServerImpl;

public class SetupIntegrationTest extends SetupTest {

	private final static String HOST_TEST = Configuration.getInstance().getServerHost();
	private final static int PORT_TEST = Configuration.getInstance().getServerPort();
	private final static int QUEUE_SIZE_TEST = Configuration.getInstance().getServerQueueSize();
	private final static int CORE_POOL_SIZE_TEST = Configuration.getInstance().getServerCorePoolSize();
	private final static int MAX_POOL_SIZE_TEST = Configuration.getInstance().getServerMaxPoolSize();
	private final static String defaultBaseURL = "http://" + HOST_TEST + ":" + PORT_TEST;
	
	private static CustomHttpServer server;
	
	protected TestClient testClient;
	
	protected SetupIntegrationTest() {
		testClient = new TestClient(defaultBaseURL);
	}
	
	@BeforeClass
	public static void setUp() throws Exception {
		server = new CustomHttpServerImpl(HOST_TEST, PORT_TEST, QUEUE_SIZE_TEST, CORE_POOL_SIZE_TEST, MAX_POOL_SIZE_TEST);
		server.run();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		server.close();
	}

}
