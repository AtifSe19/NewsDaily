package com.orion.newsdaily.newsTag;

import com.orion.newsdaily.newsArticle.NewsArticle;
import com.orion.newsdaily.newsArticle.NewsArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/newstag")
@RequiredArgsConstructor
public class NewsTagController {

    @Autowired
    private final NewsTagService newsTagService;

    @GetMapping("/{newsid}")
    public ResponseEntity<List<String>> findAll(@PathVariable("newsid") long newsId) {
        List<Long> newsTagIds = newsTagService.findTagsByNewsArticleId(newsId);

        List<String> newsTags=newsTagService.getTagNamesByTagIds(newsTagIds);
        return ResponseEntity.ok(newsTags);
    }
}
