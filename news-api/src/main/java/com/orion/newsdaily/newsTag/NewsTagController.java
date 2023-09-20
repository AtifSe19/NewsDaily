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
    public ResponseEntity<List<Long>> findAll(@PathVariable("newsid") long newsId) {
//
        List<Long> newsTags = newsTagService.findTagsByNewsArticleId(newsId);

//
        return ResponseEntity.ok(newsTags);
    }
}
