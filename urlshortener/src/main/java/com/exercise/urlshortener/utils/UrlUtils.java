package com.exercise.urlshortener.utils;

public class UrlUtils {

    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = ALPHABET.length();
    private static final long START_ID = 100000000;
	
	public static String generateShortUrl(Long urlId){
		urlId = START_ID + urlId;
		StringBuilder sb = new StringBuilder();
        while (urlId > 0) {
            sb.append(ALPHABET.charAt((int)(urlId % BASE)));
            urlId /= BASE;
        }
        return sb.reverse().toString();
	}

    public static long getLongUrl(String shortUrl) {
        long id = 0;
        for (int i = 0; i < shortUrl.length(); i++) {
            id = id * BASE + ALPHABET.indexOf(shortUrl.charAt(i));
        }
        return id;
    }
}
