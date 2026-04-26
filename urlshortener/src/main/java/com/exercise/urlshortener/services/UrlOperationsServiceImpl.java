package com.exercise.urlshortener.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exercise.urlshortener.models.ShortenResult;
import com.exercise.urlshortener.models.Url;
import com.exercise.urlshortener.models.UrlEntity;
import com.exercise.urlshortener.models.UrlResponse;
import com.exercise.urlshortener.repositories.UrlOperationsRepository;
import com.exercise.urlshortener.utils.UrlUtils;

@Service
public class UrlOperationsServiceImpl implements UrlOperationsService {
	
	private UrlOperationsRepository repository;
	
	@Autowired
	public UrlOperationsServiceImpl(UrlOperationsRepository repository) {
		this.repository = repository;
	}

	@Override
	public ShortenResult createShortUrl(String longUrl) {
	
		boolean longUrlExists = repository.checkLongUrlExists(longUrl);
       
        if (!longUrlExists) {
        	
        	UrlEntity newUrl = new UrlEntity(longUrl);
        	UrlEntity savedNewUrl = repository.save(newUrl);
        	
        	// Unique identifier is ID field in database that is converted to base62
        	String shortUrl = UrlUtils.generateShortUrl(savedNewUrl.getId());
        	
        	savedNewUrl.setShortUrl("http://localhost:8080/api/urls/" + shortUrl);
        	
        	repository.save(savedNewUrl);
        	
        	return new ShortenResult(true, new UrlResponse(shortUrl));
        }
        else {
        	return new ShortenResult(false, new UrlResponse("Invalid input or alias already taken"));
        }
	}
	
	// Use this method when custom alias provided
	@Override
	public ShortenResult createShortUrl(Url url) {
		boolean longUrlExists = repository.checkLongUrlExists(url.getLongUrl());
		boolean shortUrlExits = repository.checkShortUrlExists(url.getCustomAlias());
	       
        if (!longUrlExists && !shortUrlExits) {
        	
        	UrlEntity newUrl = new UrlEntity(url.getLongUrl(), "http://localhost:8080/api/urls/" + url.getCustomAlias());
        	repository.save(newUrl);
        	return new ShortenResult(true, new UrlResponse(url.getCustomAlias()));
        }
        else {
        	return new ShortenResult(false, new UrlResponse("Invalid input or alias already taken"));
        }
	}

	@Override
	public String getLongUrl(String shortUrl) {
		
		long urlId = repository.getUrlIdByShortUrl("http://localhost:8080/api/urls/" + shortUrl);
		UrlEntity urlEntity = repository.findById(urlId).orElseGet(null);
		
		return urlEntity.getLongUrl();
	}

	@Override
	public boolean deleteShortUrl(String shortUrl) {
		try {
			long id = repository.getUrlIdByShortUrl(shortUrl);
			repository.deleteById(id);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<UrlEntity> getAllUrls() {
		return repository.findAll();
	}
}
