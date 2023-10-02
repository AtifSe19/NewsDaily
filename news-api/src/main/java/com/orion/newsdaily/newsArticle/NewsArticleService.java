package com.orion.newsdaily.newsArticle;

import com.orion.newsdaily.auditTrail.AuditTrail;
import com.orion.newsdaily.auditTrail.AuditTrailRepo;
import com.orion.newsdaily.auditTrail.AuditTrailService;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsArticleService {

    @Autowired
    private final NewsArticleRepo newsArticleRepo;
    @Autowired
    private final UserService userService;

//    private final AuditTrailService auditTrailService;
    private final Logger logger = LoggerFactory.getLogger(getClass());
//    @PersistenceContext
//    private EntityManager entityManager;
//
//
//    public List<NewsArticle> getAdsByTagList(List<Long> taglist) {
//        return entityManager.createQuery(
//                        "SELECT DISTINCT na " +
//                                "FROM NewsArticle na " +
//                                "JOIN FETCH na.tags nt " + // Assuming "tags" is the name of the relationship in NewsArticle entity
//                                "WHERE na.isAd = true " +
//                                "AND nt.tagId IN :taglist", NewsArticle.class)
//                .setParameter("taglist", taglist)
//                .getResultList();
//    }


    @Transactional
    public NewsArticle create(NewsArticle newsArticle, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUserName(username);

        newsArticle.setUser(user);
        newsArticle.setPostedAt(LocalDateTime.now());

        newsArticle.setIsApproved(newsArticle.getIsAd());

//        if(newsArticle.getIsAd())   //same as above
//        {
//            newsArticle.setIsApproved(true);
//        }
//        else
//        {
//            newsArticle.setIsApproved(false);
//        }
        newsArticle.setIsDisabled(false);
        return newsArticleRepo.save(newsArticle);
    }

    public List<NewsArticle> findPendingNews() {
        return newsArticleRepo.findPendingNews();
    }

//    public List<NewsArticle> findMyAds() {
////        List<Long> list = auditTrailService.getUserPreferencesByUserId(auth);
//
//        // Get the current authentication object
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        AuditTrailService auditTrailService = new AuditTrailService();
//        return getAdsByTagList(auditTrailService.getUserPreferencesByUserId(authentication));
//    }

    public List<NewsArticle> findMyPendingNews(long id) {
        return newsArticleRepo.findMyPendingNews(id);
    }

    public NewsArticle findById(Long id) {
        return newsArticleRepo.findById(id).orElse(null);
    }

    public NewsArticle approveNewsToggle(long id) {
        Optional<NewsArticle> existingNewsOptional = newsArticleRepo.findById(id);
        if (existingNewsOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "News not found");
        }
        NewsArticle newsArticleToUpdate = existingNewsOptional.get();
        if (newsArticleToUpdate.getIsApproved().equals(Boolean.FALSE)) {
            newsArticleToUpdate.setIsApproved(true);
        } else if (newsArticleToUpdate.getIsApproved().equals(Boolean.TRUE)) {
            newsArticleToUpdate.setIsApproved(false);

        }
        newsArticleRepo.save(newsArticleToUpdate);
        return newsArticleToUpdate;
    }

    @Transactional
    public NewsArticle disableNewsToggle(long id) {
        Optional<NewsArticle> newsArticle = newsArticleRepo.findById(id);
        if (newsArticle.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "News not found");
        }
        NewsArticle newsArticleToUpdate = newsArticle.get();
        if (newsArticleToUpdate.getIsDisabled().equals(Boolean.FALSE)) {
            newsArticleToUpdate.setIsDisabled(true);
        } else if (newsArticleToUpdate.getIsDisabled().equals(Boolean.TRUE)) {
            newsArticleToUpdate.setIsDisabled(false);
        }
        newsArticleRepo.save(newsArticleToUpdate);
        return newsArticleToUpdate;
    }

    public void delete(long id) {
        Optional<NewsArticle> newsArticle = newsArticleRepo.findById(id);

        if (newsArticle.isPresent()) {
            newsArticleRepo.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    public List<NewsArticle> findAllNewsForEditor() {
        return newsArticleRepo.findAllNewsForEditor();
    }

    @Transactional
    public List<NewsArticle> findAll() {
        return newsArticleRepo.findAllNewsForUser();
    }
    @Transactional
    public List<NewsArticle> findFilteredNews(String tagIdList) {
        List<Long> idList = new ArrayList<>();
        String[] idStrings = tagIdList.split(",");

        for (String idString : idStrings) {
            try {
                Long id = Long.parseLong(idString.trim());
                idList.add(id);
            } catch (NumberFormatException e) {

                System.err.println("Skipping non-numeric value: " + idString);
            }
        }
        return newsArticleRepo.findFilteredNews(idList);
    }

    public NewsArticle findNewsByCommentId(Long cmtId) {
        return newsArticleRepo.findNewsByCommentId(cmtId);
    }


    public void delete(NewsArticle newsArticle) {
        newsArticleRepo.delete(newsArticle);
    }

    public List<NewsArticle> findReporterPendingNews(String username) {
        return newsArticleRepo.findReporterPendingNews(username);
    }

    public List<NewsArticle> getMyAds(List<Long> list) {
        return newsArticleRepo.getMyAds(list);
    }
}