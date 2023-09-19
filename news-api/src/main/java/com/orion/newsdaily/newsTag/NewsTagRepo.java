package com.orion.newsdaily.newsTag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsTagRepo extends JpaRepository<NewsTag, Long> {
    @Query(value = "SELECT * FROM NEWS_TAGS WHERE NEWS_ARTICLE_ID = ?1 AND TAG_ID = ?2", nativeQuery = true)
    NewsTag getIdByNTID(Long nId, Long tId);
}
