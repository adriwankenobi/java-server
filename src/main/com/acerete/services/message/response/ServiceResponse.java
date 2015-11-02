package com.acerete.services.message.response;

import com.acerete.services.message.ServiceMessage;

public interface ServiceResponse extends ServiceMessage {
	
	/**
	 * Returns the response object
	 * @return
	 */
	public Response getResponse();
}
