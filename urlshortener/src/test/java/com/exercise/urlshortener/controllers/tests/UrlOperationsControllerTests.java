package com.exercise.urlshortener.controllers.tests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.exercise.urlshortener.controllers.UrlOperationsController;
import com.exercise.urlshortener.models.ShortenResult;
import com.exercise.urlshortener.models.Url;
import com.exercise.urlshortener.models.UrlEntity;
import com.exercise.urlshortener.services.UrlOperationsService;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(UrlOperationsController.class)
public class UrlOperationsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UrlOperationsService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateShortUrl_withoutAlias_success() throws Exception {
    	UrlEntity url = new UrlEntity("https://example.com", "");

        when(service.getShortUrl(url.getLongUrl()))
                .thenReturn(new ShortenResult(true, "abc123"));

        mockMvc.perform(post("/urls/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(url)))
                .andExpect(status().isCreated())
                .andExpect(header().string("description", "URL successfully shortened"))
                .andExpect(content().string("abc123"));
    }

    @Test
    void testCreateShortUrl_withAlias_success() throws Exception {
    	UrlEntity url = new UrlEntity("https://example.com", "custom");

        when(service.getShortUrl(any(Url.class)))
                .thenReturn(new ShortenResult(true, "custom"));

        mockMvc.perform(post("/urls/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(url)))
                .andExpect(status().isCreated())
                .andExpect(content().string("custom"));
    }

    @Test
    void testCreateShortUrl_failure() throws Exception {
    	UrlEntity url = new UrlEntity("https://example.com", "");

        when(service.getShortUrl(url.getLongUrl()))
                .thenReturn(new ShortenResult(false, "error"));

        mockMvc.perform(post("/urls/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(url)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRedirect_success() throws Exception {
        when(service.getLongUrl("abc"))
                .thenReturn("https://example.com");

        mockMvc.perform(get("/urls/abc"))
                .andExpect(status().isFound()) // 302
                .andExpect(header().string("Location", "https://example.com"));
    }

    @Test
    void testRedirect_notFound() throws Exception {
        when(service.getLongUrl("abc"))
                .thenReturn("");

        mockMvc.perform(get("/urls/abc"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDelete_success() throws Exception {
        when(service.deleteShortUrl("abc")).thenReturn(true);

        mockMvc.perform(delete("/urls/abc"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDelete_notFound() throws Exception {
        when(service.deleteShortUrl("abc")).thenReturn(false);

        mockMvc.perform(delete("/urls/abc"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllUrls() throws Exception {
        when(service.getAllUrls()).thenReturn(Arrays.asList(
                new UrlEntity("https://a.com"),
                new UrlEntity("https://b.com")
        ));

        mockMvc.perform(get("/urls"))
                .andExpect(status().isOk());
    }
}