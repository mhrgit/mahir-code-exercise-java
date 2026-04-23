package com.exercise.urlshortener.models;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity(name = "url")
@Table(name = "urls")
public class UrlEntity {

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;

    @Column(name = "longurl")
    private String longUrl;

    @Column(name = "shorturl")
    private String shortUrl;

    public UrlEntity() {
    }

    public UrlEntity(Long id, String longUrl, String shortUrl) {
        this.id = id;
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullUrl() {
        return longUrl;
    }

    public void setFullUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

}
