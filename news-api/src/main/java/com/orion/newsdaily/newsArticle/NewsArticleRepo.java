package com.orion.newsdaily.newsArticle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsArticleRepo extends JpaRepository<NewsArticle, Long > {

    @Query(value = "SELECT * FROM news_articles WHERE status = 'false'", nativeQuery = true)
    List<NewsArticle> findPendingNews();
}