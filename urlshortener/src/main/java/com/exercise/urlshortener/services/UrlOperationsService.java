package com.exercise.urlshortener.services;

import java.util.List;

import com.exercise.urlshortener.models.ShortenResult;
import com.exercise.urlshortener.models.Url;
import com.exercise.urlshortener.models.UrlEntity;

public interface UrlOperationsService {

	ShortenResult getShortUrl(String longUrl);
	ShortenResult getShortUrl(Url url);
	String getLongUrl(String shortUrl);
	boolean deleteShortUrl(String url);
	List<UrlEntity> getAllUrls();
}
