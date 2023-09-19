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
@RequiredArgsConstructor
public class NewsArticleService {

    @Autowired
    private final NewsArticleRepo newsArticleRepo;
    @PersistenceContext
    private EntityManager entityManager;

    public NewsArticle create(NewsArticle newsArticle) {
        return newsArticleRepo.save(newsArticle);
    }
    public List<NewsArticle> findAll() {
//        return newsArticleRepo.findAll();

        TypedQuery<NewsArticle> query = entityManager.createQuery(
                "SELECT n FROM NewsArticle n " +
                        "WHERE n.isSponsored = false " +
                        "AND n.isApproved = true " +
                        "AND n.isDisabled = false " +
                        "ORDER BY n.postedAt DESC", NewsArticle.class);
        return query.getResultList();
    }
    public List<NewsArticle> findPendingNews()
    {
        return newsArticleRepo.findPendingNews();
    }
    public NewsArticle findById(Long id) {
        return newsArticleRepo.findById(id).orElse(null);
    }
    public NewsArticle update(NewsArticle newsToUpdate, long id) {

        Optional<NewsArticle> existingNewsOptional = newsArticleRepo.findById(id);

        if (existingNewsOptional.isEmpty()) {
            return null;
        }
        NewsArticle previous = existingNewsOptional.get();
        previous.setIsApproved(true);
        newsArticleRepo.save(previous);
        return previous;
    }
    public NewsArticle sponsored(NewsArticle newsToUpdate, long id) {

        Optional<NewsArticle> existingNewsOptional = newsArticleRepo.findById(id);

        if (existingNewsOptional.isEmpty()) {
            return null;
        }
        NewsArticle previous = existingNewsOptional.get();
        previous.setIsSponsored(true);
        newsArticleRepo.save(previous);
        return previous;
    }

    public void disableNews(long id) {
        Optional<NewsArticle> newsArticle = newsArticleRepo.findById(id);

        if (newsArticle.isPresent()) {
            newsArticleRepo.disableNews(id);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "News not found");
        }
    }
}