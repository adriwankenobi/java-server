package com.acerete.httpserver;

import com.acerete.exceptions.HttpServerException;

public interface CustomHttpServer {
	
	/**
	 * Starts the HTTP server
	 * @throws HttpServerException
	 */
	public void run() throws HttpServerException;
	
	/**
	 * Graceful shut down:
	 * Waits until the close signal is sent, then calls close()
	 * @throws HttpServerException
	 */
	public void waitForClose() throws HttpServerException;
	
	/**
	 * Stops the HTTP server
	 * @throws HttpServerException
	 */
	public void close() throws HttpServerException;

	/**
	 * Returns if server is done (stopped) or not
	 * @return
	 */
	public boolean isDone();
}
