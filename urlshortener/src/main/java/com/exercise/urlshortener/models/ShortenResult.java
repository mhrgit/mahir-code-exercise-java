package com.exercise.urlshortener.models;

public class ShortenResult {
	
	private boolean isSuccessfull;
	private UrlResponse result;
	
	public ShortenResult(boolean isSuccessfull, UrlResponse result) {
		super();
		this.isSuccessfull = isSuccessfull;
		this.result = result;
	}
	
	public boolean isSuccessfull() {
		return isSuccessfull;
	}
	public void setSuccessfull(boolean isSuccessfull) {
		this.isSuccessfull = isSuccessfull;
	}
	public UrlResponse getResult() {
		return result;
	}
	public void setResult(UrlResponse result) {
		this.result = result;
	}
	
	
}
