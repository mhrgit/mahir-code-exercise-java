package com.exercise.urlshortener.services.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.exercise.urlshortener.models.ShortenResult;
import com.exercise.urlshortener.models.Url;
import com.exercise.urlshortener.models.UrlEntity;
import com.exercise.urlshortener.models.UrlResponse;
import com.exercise.urlshortener.repositories.UrlOperationsRepository;
import com.exercise.urlshortener.services.UrlOperationsServiceImpl;

public class UrlOperationsServiceTests {

    @Mock
    private UrlOperationsRepository repository;

    @InjectMocks
    private UrlOperationsServiceImpl service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        // set @Value manually (since Spring doesn't run here)
        service = new UrlOperationsServiceImpl(repository);
        ReflectionTestUtils.setField(service, "urlBase", "http://short/");
    }

    @Test
    void shouldCreateShortUrl_success() {
        String longUrl = "https://google.com";

        UrlEntity savedEntity = new UrlEntity(longUrl);
        savedEntity.setId(1L);

        when(repository.checkLongUrlExists(longUrl)).thenReturn(false);
        when(repository.save(any(UrlEntity.class))).thenReturn(savedEntity);

        ShortenResult result = service.createShortUrl(longUrl);

        assertTrue(result.isSuccessfull());
        assertNotNull(result.getResult());
        verify(repository, times(2)).save(any(UrlEntity.class));
    }

    @Test
    void shouldFailWhenLongUrlExists() {
        String longUrl = "https://google.com";

        when(repository.checkLongUrlExists(longUrl)).thenReturn(true);

        ShortenResult result = service.createShortUrl(longUrl);

        assertFalse(result.isSuccessfull());
        assertEquals("Invalid input or alias already taken", result.getResult().getShortUrl());
    }

    @Test
    void shouldCreateShortUrlWithCustomAlias_success() {
        Url url = new Url();
        url.setLongUrl("https://google.com");
        url.setCustomAlias("abc");

        when(repository.checkLongUrlExists(url.getLongUrl())).thenReturn(false);
        when(repository.checkShortUrlExists("abc")).thenReturn(false);

        ShortenResult result = service.createShortUrl(url);

        assertTrue(result.isSuccessfull());
        verify(repository, times(1)).save(any(UrlEntity.class));
    }

    @Test
    void shouldFailWhenCustomAliasExists() {
        Url url = new Url();
        url.setLongUrl("https://google.com");
        url.setCustomAlias("abc");

        when(repository.checkShortUrlExists("abc")).thenReturn(true);

        ShortenResult result = service.createShortUrl(url);

        assertFalse(result.isSuccessfull());
    }

    @Test
    void shouldReturnLongUrl_success() {
        String shortUrl = "abc";
        long id = 1L;

        UrlEntity entity = new UrlEntity();
        entity.setLongUrl("https://google.com");

        when(repository.getUrlIdByShortUrl("http://short/abc")).thenReturn(id);
        when(repository.findById(id)).thenReturn(Optional.of(entity));

        String result = service.getLongUrl(shortUrl);

        assertEquals("https://google.com", result);
    }

    @Test
    void shouldReturnEmptyString_whenExceptionOccurs() {
        when(repository.getUrlIdByShortUrl(anyString()))
                .thenThrow(new RuntimeException("DB error"));

        String result = service.getLongUrl("abc");

        assertEquals("", result);
    }
    
    @Test
    void shouldDeleteUrl_success() {
        when(repository.getUrlIdByShortUrl(anyString())).thenReturn(1L);

        boolean result = service.deleteShortUrl("abc");

        assertTrue(result);
        verify(repository).deleteById(1L);
    }

    @Test
    void shouldReturnFalseWhenDeleteFails() {
        when(repository.getUrlIdByShortUrl(anyString()))
                .thenThrow(new RuntimeException());

        boolean result = service.deleteShortUrl("abc");

        assertFalse(result);
    }

    @Test
    void shouldReturnAllUrls() {
        when(repository.findAll()).thenReturn(List.of(new UrlEntity(), new UrlEntity()));

        List<UrlEntity> result = service.getAllUrls();

        assertEquals(2, result.size());
    }
}
