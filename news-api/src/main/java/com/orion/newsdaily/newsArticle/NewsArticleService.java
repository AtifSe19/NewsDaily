package com.orion.newsdaily.newsArticle;

import com.orion.newsdaily.user.User;
import com.orion.newsdaily.user.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsArticleService {

    @Autowired
    private final NewsArticleRepo newsArticleRepo;
    @Autowired
    private UserService userService;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @PersistenceContext
    private EntityManager entityManager;


    public NewsArticle create(NewsArticle newsArticle, Authentication authentication) {
        String username=authentication.getName();
        User user=userService.findByUserName(username);

        newsArticle.setUser(user);
        newsArticle.setPostedAt(LocalDateTime.now());
        newsArticle.setIsSponsored(false);
        newsArticle.setIsApproved(false);
        newsArticle.setIsDisabled(false);

        return newsArticleRepo.save(newsArticle);
    }
    public List<NewsArticle> findAll() {
//        Find only those news that are approved and not sponsored, and not disabled
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
        TypedQuery<NewsArticle> query = entityManager.createQuery(
                "SELECT n FROM NewsArticle n " +
                        "WHERE n.isApproved = false ", NewsArticle.class);
        return query.getResultList();
    }
    public NewsArticle findById(Long id) {
        return newsArticleRepo.findById(id).orElse(null);
    }
    public NewsArticle toggleApprovedStatus(long id) {
        Optional<NewsArticle> existingNewsOptional=newsArticleRepo.findById(id);

        if (existingNewsOptional.isEmpty()) {
            return null;
        }

        NewsArticle newsArticleToUpdate=existingNewsOptional.get();
        if(newsArticleToUpdate.getIsApproved().equals(Boolean.FALSE)){
            newsArticleToUpdate.setIsApproved(true);
        } else if (newsArticleToUpdate.getIsApproved().equals(Boolean.TRUE)) {
            newsArticleToUpdate.setIsApproved(false);

        }

        newsArticleRepo.save(newsArticleToUpdate);
        return newsArticleToUpdate;
    }
    @Transactional
    public void sponsorNewsToggle(long id) {
        Optional<NewsArticle> newsArticle = newsArticleRepo.findById(id);

        if (newsArticle.isPresent()) {
            if(newsArticle.get().getIsSponsored().equals(Boolean.FALSE)){
                newsArticleRepo.sponsored(id);
            } else if (newsArticle.get().getIsSponsored().equals(Boolean.TRUE)) {
                newsArticleRepo.notsponsored(id);

            }
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "News not found");
        }
    }

    @Transactional
    public void disableNewsToggle(long id) {
        Optional<NewsArticle> newsArticle = newsArticleRepo.findById(id);

        if (newsArticle.isPresent()) {
            if(newsArticle.get().getIsDisabled().equals(Boolean.FALSE)){
            newsArticleRepo.disableNews(id);
            } else if (newsArticle.get().getIsDisabled().equals(Boolean.TRUE)) {
                newsArticleRepo.enableNews(id);

            }

        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "News not found");
        }
    }
}