package com.exercise.urlshortener.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exercise.urlshortener.models.UrlEntity;

@Repository
public interface UrlOperationsRepository extends JpaRepository<UrlEntity, Long> {

	@Query(value = "SELECT COUNT(*) > 0 FROM urls u WHERE u.longUrl = :longUrl")
	boolean checkLongUrlExists(@Param("longUrl") String longUrl);
	
	@Query(value = "SELECT longUrl FROM urls u WHERE u.shortUrl = :shortUrl")
	String getLongUrl(String shortUrl);
}
