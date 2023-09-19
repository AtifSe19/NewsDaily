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
    private NewsArticleService service;

    //-------------REPORTER

    @PostMapping
    public ResponseEntity<NewsArticle> create(@RequestBody NewsArticle newsArticle) {

        NewsArticle created = service.create(newsArticle);
        if (created != null) {
            return ResponseEntity.ok(created);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    //---------USER & ADMIN

    //Get all news that are approved and every user can see!
    @GetMapping
    public ResponseEntity<List<NewsArticle>> findAll() {
        List<NewsArticle> newsArticles = service.findAll();
        return ResponseEntity.ok(newsArticles);

    }

    //-----------EDITOR

    @GetMapping("/pending")
    public ResponseEntity<List<NewsArticle>> findPendingNews() {
        return  ResponseEntity.ok(service.findPendingNews());
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsArticle> updateStatus(@PathVariable("id") long id,@RequestBody NewsArticle newsArticle) {

        NewsArticle up = service.update(newsArticle, id);
        if (up==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(up);
    }

    @PutMapping("/{id}/sponsored")
    public ResponseEntity<NewsArticle> sponsored(@PathVariable("id") long id,@RequestBody NewsArticle newsArticle) {

        NewsArticle up = service.sponsored(newsArticle, id);
        if (up==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(up);
    }

    //---------ADMIN-----EDITOR

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        NewsArticle newsArticle = service.findById(id);
        if (newsArticle==null) {
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}