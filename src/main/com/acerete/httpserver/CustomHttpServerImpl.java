package com.acerete.httpserver;

import java.net.InetSocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.acerete.exceptions.HttpServerException;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpContext;

public class CustomHttpServerImpl implements CustomHttpServer {

	private final static long MINUTES_TO_WAIT_UNTIL_TERMINATION = 120l;
	
	// ERROR strings
	private final static String SERVER_NOT_INITIALIZED_YET = "The server has not been initialized yet!";
	private final static String SERVER_INITIALIZATION_ERROR = "There was a problem while initiliating the server!";
	private final static String SERVER_GRACEFUL_SHUTDOWN_ERROR = "There was a problem during the graceful shutdown!";
	
	// MESSAGE strings
	private final static String SERVER_STARTING = "Starting server ...";
	private final static String SERVER_STARTED = "Server started!";
	private final static String SERVER_STOPPING = "Stopping server ...";
	private final static String SERVER_STOPPED = "Server stopped!";
	
	private HttpServer server;
	private ExecutorService executor;
	private boolean isDone = true;
	
	public CustomHttpServerImpl(String host, int port, int queueSize, int corePoolSize, int maxPoolSize) throws HttpServerException {
		
		try {
			
			InetSocketAddress inetSocketAddress = new InetSocketAddress(host, port);
			server = HttpServer.create(inetSocketAddress, 0);
			
			HttpContext context = server.createContext("/", new HttpRequestsHandler());
			
	        context.getFilters().add(new ParamsFilter());
	        
	        final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(queueSize);
	        executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 0L, TimeUnit.MILLISECONDS, queue);
	        server.setExecutor(executor);
			
		} catch (Exception e) {
			resetServerAttributes();
			throw new HttpServerException(SERVER_INITIALIZATION_ERROR, e);
		}
	}
	
	private void resetServerAttributes() {
		this.server = null;
		this.executor = null;
	}
	
	private boolean isServerInitialized() {
		return server != null && executor != null;
	}
	
	@Override
	public void run() throws HttpServerException {
		if (!isServerInitialized()) {
			throw new HttpServerException(SERVER_NOT_INITIALIZED_YET);
		}
		else {
			System.out.println(SERVER_STARTING);
			server.start();
			isDone = false;
			System.out.println(SERVER_STARTED);
		}
	}

	@Override
	public void waitForClose() throws HttpServerException {
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() { 
		    	close();
		    	if (isServerInitialized()) {
		    		throw new HttpServerException(SERVER_GRACEFUL_SHUTDOWN_ERROR);
		    	}
		    }
		});
	}
	
	@Override
	public void close() throws HttpServerException {
		if (!isServerInitialized()) {
			throw new HttpServerException(SERVER_NOT_INITIALIZED_YET);
		}
		else {
			try {
				System.out.println(SERVER_STOPPING);
				server.stop(0);
				executor.shutdown();
				executor.awaitTermination(MINUTES_TO_WAIT_UNTIL_TERMINATION, TimeUnit.MINUTES);
				resetServerAttributes();
				isDone = true;
				System.out.println(SERVER_STOPPED);
			}
			catch (Exception e) {
				throw new HttpServerException(SERVER_GRACEFUL_SHUTDOWN_ERROR, e);
			}
		}
	}
	
	@Override
	public boolean isDone() {
		return isDone;
	}
	
}
