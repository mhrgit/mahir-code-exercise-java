package com.exercise.urlshortener.utils.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.exercise.urlshortener.utils.UrlUtils;

public class UrlUtilsTests {

    @Test
    void testGenerateShortUrl_basicValues() {
        assertEquals("1", UrlUtils.generateShortUrl(1L));
        assertEquals("a", UrlUtils.generateShortUrl(10L));
        assertEquals("Z", UrlUtils.generateShortUrl(61L));
    }

    @Test
    void testGenerateShortUrl_andBack() {
        long[] testValues = {1L, 10L, 61L, 62L, 12345L, 999999L, Long.MAX_VALUE};

        for (long value : testValues) {
            String shortUrl = UrlUtils.generateShortUrl(value);
            long result = UrlUtils.getLongUrl(shortUrl);
            assertEquals(value, result, "Failed for value: " + value);
        }
    }

    @Test
    void testGetLongUrl_basicValues() {
        assertEquals(1L, UrlUtils.getLongUrl("1"));
        assertEquals(10L, UrlUtils.getLongUrl("a"));
        assertEquals(61L, UrlUtils.getLongUrl("Z"));
    }

    @Test
    void testGenerateShortUrl_zero() {
        // Edge case: current implementation returns empty string for 0
        assertEquals("", UrlUtils.generateShortUrl(0L));
    }

    @Test
    void testGetLongUrl_emptyString() {
        assertEquals(0L, UrlUtils.getLongUrl(""));
    }

    @Test
    void testConsistency_randomValues() {
        for (long i = 1; i < 10000; i++) {
            String shortUrl = UrlUtils.generateShortUrl(i);
            long decoded = UrlUtils.getLongUrl(shortUrl);
            assertEquals(i, decoded);
        }
    }
}