package com.exercise.urlshortener.utils.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.exercise.urlshortener.utils.UrlUtils;

public class UrlUtilsTests {

	@Test
    void shouldGenerateShortUrl() {
        String shortUrl = UrlUtils.generateShortUrl(1L);

        assertNotNull(shortUrl);
        assertFalse(shortUrl.isEmpty());
    }

    @Test
    void shouldGenerateDifferentShortUrlsForDifferentIds() {
        String url1 = UrlUtils.generateShortUrl(1L);
        String url2 = UrlUtils.generateShortUrl(2L);

        assertNotEquals(url1, url2);
    }

    @Test
    void shouldDecodeShortUrlToLong() {
        Long originalId = 12345L;

        String shortUrl = UrlUtils.generateShortUrl(originalId);
        long decoded = UrlUtils.getLongUrl(shortUrl);

        // NOTE: decoded includes START_ID offset
        assertEquals(100000000L + originalId, decoded);
    }

    @Test
    void shouldBeConsistentEncodeDecode() {
        Long[] testIds = {1L, 10L, 999L, 123456L, 9999999L};

        for (Long id : testIds) {
            String shortUrl = UrlUtils.generateShortUrl(id);
            long decoded = UrlUtils.getLongUrl(shortUrl);

            assertEquals(100000000L + id, decoded);
        }
    }

    @Test
    void shouldHandleZeroId() {
        String shortUrl = UrlUtils.generateShortUrl(0L);

        assertNotNull(shortUrl);
        assertFalse(shortUrl.isEmpty());

        long decoded = UrlUtils.getLongUrl(shortUrl);
        assertEquals(100000000L, decoded);
    }

    @Test
    void shouldHandleLargeNumbers() {
        Long largeId = Long.MAX_VALUE / 1000;

        String shortUrl = UrlUtils.generateShortUrl(largeId);
        long decoded = UrlUtils.getLongUrl(shortUrl);

        assertEquals(100000000L + largeId, decoded);
    }

    @Test
    void shouldDecodeKnownValue() {
        // Optional deterministic test
        long decoded = UrlUtils.getLongUrl("a");

        assertTrue(decoded >= 0);
    }
}