package com.svilen.onlinebookstore.repository;

import com.svilen.onlinebookstore.domain.entities.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, String> {
    Optional<News> findByName(String name);
}
