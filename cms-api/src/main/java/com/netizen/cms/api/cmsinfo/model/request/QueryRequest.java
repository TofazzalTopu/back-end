package com.netizen.cms.api.cmsinfo.model.request;

public class QueryRequest {
	private String query;
	private String type;
	private String secrate = "";

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSecrate() {
		return secrate;
	}

	public void setSecrate(String secrate) {
		this.secrate = secrate;
	}

}