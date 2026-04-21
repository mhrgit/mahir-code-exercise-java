package com.exercise.urlshortener.models;
import jakarta.validation.constraints.NotBlank;

public class Url {
	
	@NotBlank(message = "Full URL cannot be empty")
	private String fullUrl;
	private String customAlias;
	
	public String getFullUrl() {
		return fullUrl;
	}
	public void setFullUrl(String fullUrl) {
		this.fullUrl = fullUrl;
	}
	public String getCustomAlias() {
		return customAlias;
	}
	public void setCustomAlias(String customAlias) {
		this.customAlias = customAlias;
	}
}
