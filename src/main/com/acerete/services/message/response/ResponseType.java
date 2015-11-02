package com.acerete.services.message.response;

import com.acerete.exceptions.FileNameNotSetException;
import com.acerete.services.message.response.ResponseType;

public enum ResponseType {
	
	/**
	 * UniqueId: Assign unique id for each ResponseType
	 * IsCSV: Response is CSV or just text
	 * FileName: File name in case of CSV response
	 */
	EXCEPTION("exception", false, null),
	LOGIN("login", false, null),
	POST_USER_SCORE_TO_LEVEL("score", false, null),
	GET_HIGH_SCORE_LIST_FOR_LEVEL("highscorelist", true, "highscorelist");
	
	// ERROR strings
	private final static String ERROR_FILENAME_NOT_SET = "Filename not set";
		
	private String id;
	private Boolean isCSV;
	private String fileName;
	
	private ResponseType(String id, boolean isCSV, String fileName) {
		this.id = id;
		this.isCSV = isCSV;
		this.fileName = fileName;
	}

	public String getId() {
		return id;
	}
	
	public boolean isCSV() {
		return isCSV;
	}
	
	public String getFileName() {
		if (fileName != null && !fileName.equals("")) {
			return fileName;
		}
		else {
			throw new FileNameNotSetException(ERROR_FILENAME_NOT_SET);
		}
	}
}
