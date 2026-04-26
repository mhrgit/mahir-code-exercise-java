package com.exercise.urlshortener.models;

public class UrlResponse {
	
	private String shortUrl;

	public UrlResponse(String shortUrl) {
		super();
		this.shortUrl = shortUrl;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}	
}
