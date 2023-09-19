package com.orion.newsdaily.newsArticle;

import com.orion.newsdaily.user.User;
import com.orion.newsdaily.user.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
public class NewsArticleController {

    @Autowired
    private final NewsArticleService newsArticleService;

    @Autowired
    private UserService userService;
    private final Logger logger = LoggerFactory.getLogger(getClass());


    //-------------REPORTER

    @PreAuthorize("hasAuthority('REPORTER')")
    @PostMapping
    public ResponseEntity<NewsArticle> create(Authentication authentication, @RequestBody NewsArticle newsArticle) {

        NewsArticle created = newsArticleService.create(newsArticle,authentication);
        if (created != null) {
            return ResponseEntity.ok(created);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    //---------USER & ADMIN

    //Get all news that are approved and every user can see!
    @GetMapping
    public ResponseEntity<List<NewsArticle>> findAll() {
        logger.debug("In news article find all:");

        List<NewsArticle> newsArticles = newsArticleService.findAll();
        return ResponseEntity.ok(newsArticles);

    }

    //-----------EDITOR

    @PreAuthorize("hasAuthority('EDITOR')")
    @GetMapping("/pending")
    public ResponseEntity<List<NewsArticle>> findPendingNews() {
        return  ResponseEntity.ok(newsArticleService.findPendingNews());
    }


    //Give id of news which editor wants to approve
    @PreAuthorize("hasAuthority('EDITOR')")
    @PutMapping("/{id}")
    public ResponseEntity<NewsArticle> updatePendingStatus(@PathVariable("id") long id) {

        NewsArticle updated = newsArticleService.update(id);
        if (updated==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/sponsored")
    public ResponseEntity<NewsArticle> sponsored(@PathVariable("id") long id,@RequestBody NewsArticle newsArticle) {

        NewsArticle up = newsArticleService.sponsored(newsArticle, id);
        if (up==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(up);
    }

    //---------ADMIN-----EDITOR

    @PutMapping("/disable/{id}")
    public ResponseEntity<Void> disableNews(@PathVariable("id") long id) {
        NewsArticle newsArticle = newsArticleService.findById(id);
        if (newsArticle==null) {
            return ResponseEntity.notFound().build();
        }
        newsArticleService.disableNews(id);
        return ResponseEntity.noContent().build();
    }

}