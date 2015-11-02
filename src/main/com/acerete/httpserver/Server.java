package com.acerete.httpserver;

import com.acerete.Configuration;
import com.acerete.exceptions.HttpServerException;

public class Server {

    public static void main(String[] args) throws Exception {

    	try {
    		
    		// Initialize configuration
    		String host = Configuration.getInstance().getServerHost();
    		int port = Configuration.getInstance().getServerPort();
    		int queueSize = Configuration.getInstance().getServerQueueSize();
    		int corePoolSize = Configuration.getInstance().getServerCorePoolSize();
    		int maxPoolSize = Configuration.getInstance().getServerMaxPoolSize();
    		
    		// Initialize server
    		CustomHttpServer server = new CustomHttpServerImpl(host, port, queueSize, corePoolSize, maxPoolSize);
    		
    		// Run server
    		server.run();
            System.out.println("The server is listening on " + host + ":" + port + "!");
            
            // Wait until the server stops
            server.waitForClose();
    	}
    	catch (HttpServerException e) {
    		System.err.println(e.getMessage());
    		e.printStackTrace();
    	}
    	
    }
}