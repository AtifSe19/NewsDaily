package com.orion.newsdaily.newsArticle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsArticleRepo extends JpaRepository<NewsArticle, Long > {

    @Query(value = "SELECT * FROM news_articles WHERE is_approved = 'false'", nativeQuery = true)
    List<NewsArticle> findPendingNews();

    @Modifying
    @Query(value = "UPDATE news_articles SET is_disabled = 'true' WHERE id = ?1", nativeQuery = true)
    void disableNews(long id);

    @Modifying
    @Query(value = "UPDATE news_articles SET is_disabled = 'false' WHERE id = ?1", nativeQuery = true)
    void enableNews(long id);

    @Modifying
    @Query(value = "UPDATE news_articles SET is_sponsored = 'true' WHERE id = ?1", nativeQuery = true)
    void sponsored(long id);

    @Modifying
    @Query(value = "UPDATE news_articles SET is_sponsored = 'false' WHERE id = ?1", nativeQuery = true)
    void notsponsored(long id);
}