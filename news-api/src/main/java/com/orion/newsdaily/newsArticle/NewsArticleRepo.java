package com.orion.newsdaily.newsArticle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsArticleRepo extends JpaRepository<NewsArticle, Long > {
    @Query(value = "SELECT * FROM news_articles WHERE is_approved = false", nativeQuery = true)
    List<NewsArticle> findPendingNews();
    @Query(value = "SELECT * FROM news_articles WHERE is_approved = true AND is_disabled = false", nativeQuery = true)
    List<NewsArticle> findAllNews();

    @Query(value = "SELECT * FROM news_articles WHERE is_approved = true AND is_disabled = false AND is_sponsored = false", nativeQuery = true)
    List<NewsArticle> findAllNotSponsored();

    @Query(value = "SELECT * FROM news_articles WHERE is_approved = true AND is_disabled = false AND is_sponsored = true", nativeQuery = true)
    List<NewsArticle> findAllSponsored();
}