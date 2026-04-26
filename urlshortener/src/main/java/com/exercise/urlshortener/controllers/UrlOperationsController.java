package com.exercise.urlshortener.controllers;

import java.io.IOException;
import java.util.List;

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
import com.exercise.urlshortener.models.UrlEntity;
import com.exercise.urlshortener.services.UrlOperationsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/urls")
public class UrlOperationsController {

	private UrlOperationsService service;
	
	public UrlOperationsController(UrlOperationsService service) {
		this.service = service;
	}
	
    @PostMapping("/shorten")
    public ResponseEntity<Object> createShortUrl(@RequestBody Url url, HttpServletRequest request) {
    	
    	// When custom alias provided
    	if(url.getCustomAlias() != null && !url.getCustomAlias().isEmpty()) {
    		
    		ShortenResult result = service.createShortUrl(url);
    		
    		if(result.isSuccessfull()) {
    			return ResponseEntity.status(201)
    					.header("description", "URL successfully shortened")
    					.body(result.getResult());
    		} else {
    			return ResponseEntity.status(400)
    					.header("description", "Invalid input or alias already taken")
    					.body(result.getResult());
    		}
    	
    	}
    	
    	// When custom alias not provided
    	ShortenResult result = service.createShortUrl(url.getLongUrl());

    	if(result.isSuccessfull()) {
			return ResponseEntity.status(201)
					.header("description", "URL successfully shortened")
					.body(result.getResult());
    	} else {
			return ResponseEntity.status(400)
					.header("description", "Invalid input or alias already taken")
					.body(result.getResult());
    	}
    }

    @GetMapping("/{alias}")
    public void redirectToLongUrl(HttpServletResponse response, @PathVariable String alias) {
    	
    	try {
    		
    		String longUrl = service.getLongUrl(alias);
    		
    		if(longUrl != null && !longUrl.isEmpty()) {
    			response.setStatus(302);
    			response.setHeader("description", " Redirect to the original URL");
    			response.sendRedirect(longUrl);
    		} else {
    			response.setHeader("description", "Alias not found");
    			response.sendError(404);
    		}
    	
			
		} catch (IOException e) {
			e.printStackTrace();
			try {
    			response.setHeader("description", "Error occured. Alias not found");
    			response.sendError(404);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
    }
    
    @DeleteMapping("/**")
    public ResponseEntity<Object> deleteUrl(HttpServletRequest request) {
    	String shortenString = request.getRequestURI()
    	        .substring(request.getRequestURI().indexOf("/urls/") + 6);
    	
    	boolean isDeleteSuccessfull = service.deleteShortUrl(shortenString);
    	
    	if(isDeleteSuccessfull) {
			return ResponseEntity.status(204)
					.header("description", "Successfully deleted").build();
    	} else {
			return ResponseEntity.status(404)
					.header("description", "Alias not found").build();
    	}
    	
    }
    
    @GetMapping("/urls")
    public ResponseEntity<List<UrlEntity>> getAllUrls() {
		List<UrlEntity> urls = service.getAllUrls();
    	return ResponseEntity.status(200)
				.header("description", "Successfully deleted").body(urls);
    }
}
