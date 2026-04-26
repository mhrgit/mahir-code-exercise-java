package com.exercise.urlshortener.models;
import jakarta.validation.constraints.NotBlank;

public class Url {
	
	@NotBlank(message = "Long URL cannot be empty")
	private String longUrl;
	private String customAlias;
	
	public String getLongUrl() {
		return longUrl;
	}
	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}
	public String getCustomAlias() {
		return customAlias;
	}
	public void setCustomAlias(String customAlias) {
		this.customAlias = customAlias;
	}
}
