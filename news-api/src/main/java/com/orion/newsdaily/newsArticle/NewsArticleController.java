package com.orion.newsdaily.newsArticle;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
public class NewsArticleController {

    @Autowired
    private final NewsArticleService newsArticleService;

    //-------------REPORTER

    @PostMapping
    public ResponseEntity<NewsArticle> create(@RequestBody NewsArticle newsArticle) {

        NewsArticle created = newsArticleService.create(newsArticle);
        if (created != null) {
            return ResponseEntity.ok(created);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    //---------USER & ADMIN

    //Get all news that are approved and every user can see!
    @GetMapping
    public ResponseEntity<List<NewsArticle>> findAll() {
        List<NewsArticle> newsArticles = newsArticleService.findAll();
        return ResponseEntity.ok(newsArticles);

    }

    //-----------EDITOR

    @GetMapping("/pending")
    public ResponseEntity<List<NewsArticle>> findPendingNews() {
        return  ResponseEntity.ok(newsArticleService.findPendingNews());
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsArticle> updateStatus(@PathVariable("id") long id,@RequestBody NewsArticle newsArticle) {

        NewsArticle up = newsArticleService.update(newsArticle, id);
        if (up==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(up);
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