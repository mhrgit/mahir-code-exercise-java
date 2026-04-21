package com.exercise.urlshortener.controllers;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.exercise.urlshortener.models.Url;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class UrlOperationsController {

    @PostMapping("/shorten")
    public ResponseEntity<Object> createShortUrl(@RequestBody Url url, HttpServletRequest request) {
    		
    	String shortUrl = "sampleurl";
    	
    	//TODO: Integrate to the service

        return new ResponseEntity<>(shortUrl, HttpStatus.OK);
    }

    @GetMapping("/{alias}")
    public void redirectToLongUrl(HttpServletResponse response, @PathVariable String shortUrl) {
    		//TODO: Get long url and perform redirection
    	
    	try {
			response.sendRedirect("Long Url");
		} catch (IOException e) {
			e.printStackTrace();
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
