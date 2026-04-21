package com.exercise.urlshortener.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exercise.urlshortener.models.UrlEntity;

@Repository
public interface UrlOperationsRepository extends JpaRepository<UrlEntity, Long> {

}
