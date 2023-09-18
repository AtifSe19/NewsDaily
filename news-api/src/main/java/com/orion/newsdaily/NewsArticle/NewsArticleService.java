package com.orion.newsdaily.NewsArticle;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsArticleService {

    private final NewsArticleRepo repository;

    public NewsArticle create(NewsArticle newsArticle) {
        return repository.save(newsArticle);
    }
    public List<NewsArticle> findAll() {
        return repository.findAll();
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
