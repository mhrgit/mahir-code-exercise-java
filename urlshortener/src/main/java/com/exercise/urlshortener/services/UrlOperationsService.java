package com.exercise.urlshortener.services;

import com.exercise.urlshortener.models.ShortenResult;
import com.exercise.urlshortener.models.Url;

public interface UrlOperationsService {

	ShortenResult getShortUrl(String longUrl);
	ShortenResult getShortUrl(Url url);
	String getLongUrl(String shortUrl);
	boolean deleteShortUrl(String url);
}
