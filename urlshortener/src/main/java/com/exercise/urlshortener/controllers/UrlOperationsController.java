package com.exercise.urlshortener.controllers;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.urlshortener.models.ShortenResult;
import com.exercise.urlshortener.models.Url;
import com.exercise.urlshortener.services.UrlOperationsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/urls")
public class UrlOperationsController {

	private UrlOperationsService service;
	
	public UrlOperationsController(UrlOperationsService service) {
		this.service = service;
	}
	
    @PostMapping("/shorten")
    public ResponseEntity<Object> createShortUrl(@RequestBody Url url, HttpServletRequest request) {
    	
    	if(!url.getCustomAlias().isEmpty()) {
    		
    		ShortenResult result = service.getShortUrl(url);
    		
    		if(result.isSuccessfull()) {
    			return new ResponseEntity<>(result.getResult(), HttpStatus.OK);
    		} else {
    			return new ResponseEntity<>(result.getResult(), HttpStatus.BAD_REQUEST);
    		}
    	
    	}
    	
    	ShortenResult result = service.getShortUrl(url.getFullUrl());

    	if(result.isSuccessfull()) {
    		return new ResponseEntity<>(result.getResult(), HttpStatus.OK);
    	} else {
    		return new ResponseEntity<>(result.getResult(), HttpStatus.BAD_REQUEST);
    	}
    }

    @GetMapping("/{alias}")
    public void redirectToLongUrl(HttpServletResponse response, @PathVariable String shortUrl) {
    	
    	try {
    		
    		String longUrl = service.getLongUrl(shortUrl);
    		
    		if(longUrl != null && !longUrl.isEmpty()) {
    			response.setStatus(302);
    			response.sendRedirect(longUrl);
    		} else {
    			response.sendError(404);
    		}
    	
			
		} catch (IOException e) {
			e.printStackTrace();
			try {
				response.sendError(404);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
    }
    
    @DeleteMapping("/{alias}")
    public void deleteUrl(@PathVariable String shortenString) {
    		//TODO: Get long url and perform redirection
    }
    
    @GetMapping("/urls")
    public void getAllUrls(HttpServletResponse response, @PathVariable String shortenString) {
    		//TODO: Get long url and perform redirection
    }
}
