package com.orion.newsdaily.newsArticle;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class NewsArticleService {

    @Autowired
    private NewsArticleRepo repository;
    @PersistenceContext
    private EntityManager entityManager;

    public NewsArticle create(NewsArticle newsArticle) {
        return repository.save(newsArticle);
    }
    public List<NewsArticle> findAll() {
//        return repository.findAll();

        TypedQuery<NewsArticle> query = entityManager.createQuery(
                "SELECT n FROM NewsArticle n " +
                        "WHERE n.isSponsored = false " +
                        "AND n.status = true " +
                        "ORDER BY n.postedAt DESC", NewsArticle.class);
        return query.getResultList();
    }
    public List<NewsArticle> findPendingNews()
    {
        return repository.findPendingNews();
    }
    public NewsArticle findById(Long id) {
        return repository.findById(id).orElse(null);
    }
    public void delete(long id) {
        Optional<NewsArticle> newsArticle = repository.findById(id);

        if (newsArticle.isPresent()) {
            repository.deleteById(id);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }
    public NewsArticle update(NewsArticle newsToUpdate, long id) {

        Optional<NewsArticle> existingNewsOptional = repository.findById(id);

        if (existingNewsOptional.isEmpty()) {
            return null;
        }
        NewsArticle previous = existingNewsOptional.get();
        previous.setStatus(true);
        repository.save(previous);
        return previous;
    }
    public NewsArticle sponsored(NewsArticle newsToUpdate, long id) {

        Optional<NewsArticle> existingNewsOptional = repository.findById(id);

        if (existingNewsOptional.isEmpty()) {
            return null;
        }
        NewsArticle previous = existingNewsOptional.get();
        previous.setIsSponsored(true);
        repository.save(previous);
        return previous;
    }
}