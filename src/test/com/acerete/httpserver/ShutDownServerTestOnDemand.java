package com.acerete.httpserver;

import static org.junit.Assert.assertTrue;

import com.acerete.Configuration;

public class ShutDownServerTestOnDemand {

	private static final int DELAY_IN_MILLI_SECONDS = 5000;
	
	private final static String HOST_TEST = Configuration.getInstance().getServerHost();
	private final static int PORT_TEST = Configuration.getInstance().getServerPort();
	private final static int QUEUE_SIZE_TEST = Configuration.getInstance().getServerQueueSize();
	private final static int CORE_POOL_SIZE_TEST = Configuration.getInstance().getServerCorePoolSize();
	private final static int MAX_POOL_SIZE_TEST = Configuration.getInstance().getServerMaxPoolSize();
	
	private CustomHttpServer server = new CustomHttpServerImpl(HOST_TEST, PORT_TEST, QUEUE_SIZE_TEST, CORE_POOL_SIZE_TEST, MAX_POOL_SIZE_TEST);
	
	//@Test //test on demand
	public void testShouldShutDownServerManually() throws Exception {
		testShouldShutDownServer(true);
	}
	
	//@Test //test on demand (only UNIX systems)
	public void testShouldShutDownServerSignal() throws Exception {
		testShouldShutDownServer(false);
	}
	
	private void testShouldShutDownServer(final boolean manually) throws Exception {
		// New thread that will kill the server after some time
		new Thread()
        {
            public void run() {
            	// Wait some time
            	try {
            		Thread.sleep(DELAY_IN_MILLI_SECONDS);
            	}
            	catch (Exception ex) {
            		assertTrue("Something went wrong", false);
            	}
            	finally {
            		// Now kill the server
            		assertTrue("Server should not be done", !isDone());
            		if (manually) {
            			stopServer();
            		}
            		else {
            			sendKillSignal();
            		}
            		
            		// Wait more time
            		try {
                		Thread.sleep(DELAY_IN_MILLI_SECONDS);
                	}
                	catch (Exception ex) {
                		assertTrue("Something went wrong", false);
                	}
            		finally {
            			// Server should be stopped
            			assertTrue("Server should be done", isDone());
            		}
            	}
            }
        }.start();

        // Start the server
		startServer();
		
	}
	
	private void startServer() {
		server.run();
		server.waitForClose();
	}
	
	private void stopServer() {
		server.close();
	}
	
	private boolean isDone() {
		return server.isDone();
	}
	
	// Only UNIX
	private void sendKillSignal() {
		try {
			int pid = getPID();
		    Runtime.getRuntime().exec("kill " + pid).waitFor();
		}
		catch (Exception ex) {
			assertTrue("Something went wrong", false);
		}
	}
	
	private int getPID() {
		String tmp = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
		tmp = tmp.split("@")[0];
		return Integer.valueOf(tmp);
	}
}
