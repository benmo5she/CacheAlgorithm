package com.hit.server;

import java.io.Serializable;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Request<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private T body;
	private Map<String, String> headers;

	public Request(Map<String, String> headers, T body) {
		this.setHeaders(headers);
		this.setBody(body);
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	@Override
	public String toString() {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		return gson.toJson(this);
	}
}
