package com.exercise.urlshortener.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exercise.urlshortener.models.ShortenResult;
import com.exercise.urlshortener.models.Url;
import com.exercise.urlshortener.models.UrlEntity;
import com.exercise.urlshortener.repositories.UrlOperationsRepository;
import com.exercise.utils.UrlUtils;

@Service
public class UrlOperationsServiceImpl implements UrlOperationsService {
	
	private UrlOperationsRepository repository;
	
	@Autowired
	public UrlOperationsServiceImpl(UrlOperationsRepository repository) {
		this.repository = repository;
	}

	@Override
	public ShortenResult getShortUrl(String longUrl) {
	
		boolean longUrlExists = repository.checkLongUrlExists(longUrl);
       
        if (!longUrlExists) {
        	
        	String shortUrl = UrlUtils.generateShortUrl(longUrl);
        	
        	UrlEntity newUrl = new UrlEntity(longUrl, shortUrl);
        	repository.save(newUrl);
        	return new ShortenResult(true, shortUrl);
        }
        else {
        	return new ShortenResult(false, "Invalid input or alias already taken");
        }
	}
	
	@Override
	public ShortenResult getShortUrl(Url url) {
		boolean longUrlExists = repository.checkLongUrlExists(url.getFullUrl());
	       
        if (!longUrlExists) {
        	
        	UrlEntity newUrl = new UrlEntity(url.getFullUrl(), url.getCustomAlias());
        	repository.save(newUrl);
        	return new ShortenResult(true, url.getCustomAlias());
        }
        else {
        	return new ShortenResult(false, "Invalid input or alias already taken");
        }
	}

	@Override
	public String getLongUrl(String shortUrl) {
		
		return repository.getLongUrl(shortUrl);
	}

	@Override
	public boolean deleteShortUrl(String url) {
		//TODO: check if url exists and then delete
		return false;
	}
}
