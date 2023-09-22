package com.orion.newsdaily.newsTag;

import com.orion.newsdaily.newsArticle.NewsArticle;
import com.orion.newsdaily.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NewsTagService {

    @Autowired
    public NewsTagRepo newsTagRepo;

    public List<Long> findTagsByNewsArticleId(long newsId) {
        return newsTagRepo.findAllByNewsArticleId(newsId);
    }

    public NewsTag create(Long newsId, Long tagId) {
        NewsTag newsTag= new NewsTag();
        newsTag.setNewsArticleId(newsId);
        newsTag.setTagId(tagId);
        return newsTagRepo.save(newsTag);
    }
}
