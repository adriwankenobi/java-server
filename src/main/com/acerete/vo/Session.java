package com.acerete.vo;

import java.util.Date;

import com.acerete.utils.DateUtils;

public class Session {

	private Integer uid;
	private Date date;
	
	public Session(Integer uid, Date date) {
		this.uid = uid;
		this.date = date;
	}
	
	public Integer getUid() {
		return uid;
	}
	
	public Date getDate() {
		return date;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Session [uid=").append(uid)
				.append(", date=").append(DateUtils.formatIso8601Date(date)).append("]");
		return builder.toString();
	}
}
