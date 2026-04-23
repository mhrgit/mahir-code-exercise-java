package com.exercise.urlshortener.models;

public class ShortenResult {
	
	private boolean isSuccessfull;
	private String result;
	
	public ShortenResult(boolean isSuccessfull, String result) {
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
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
}
