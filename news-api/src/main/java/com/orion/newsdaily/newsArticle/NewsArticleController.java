package com.orion.newsdaily.newsArticle;

import com.orion.newsdaily.newsTag.NewsTag;
import com.orion.newsdaily.newsTag.NewsTagService;
import com.orion.newsdaily.user.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
    private NewsTagService newsTagService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PreAuthorize("hasAnyAuthority('REPORTER', 'EDITOR')")
    @PostMapping
    public ResponseEntity<NewsArticle> create(
            Authentication authentication,
            @RequestBody NewsArticle newsArticle,
            @RequestParam(name = "tags") List<Integer> selectedTags
    ) {

        NewsArticle created = newsArticleService.create(newsArticle,authentication);
        if (created != null) {
            //newsTagService.
            return ResponseEntity.ok(created);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    //---------USER & ADMIN

    //Get all news that are approved and NOT ADS for every user !
    @GetMapping
    public ResponseEntity<List<NewsArticle>> findAll(@AuthenticationPrincipal OAuth2User principal,
                                          @RequestParam(name = "email", defaultValue = "") String email,
                                          @RequestParam(name = "name", defaultValue = "") String name)
    {
//        logger.debug("In news article find all:");

//        if (principal != null) {
//            Map<String, Object> tokenAttributes = principal.getAttributes();
//            email = (String) tokenAttributes.get("email");
//        }

        List<NewsArticle> newsArticles = newsArticleService.findAll();
//        List<NewsArticle> news = newsArticleService.findAllNotSponsored();
//        List<NewsArticle> ads = newsArticleService.findAllSponsored();
//
//        List<NewsArticle> combinedNews = new ArrayList<>(news);
//        combinedNews.addAll(ads);
//        Collections.shuffle(combinedNews);

        return ResponseEntity.ok(newsArticles);
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @GetMapping("/pending")
    public ResponseEntity<List<NewsArticle>> findPendingNews() {
        return ResponseEntity.ok(newsArticleService.findPendingNews());
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @PutMapping("/approve/{id}")
    public ResponseEntity<NewsArticle> approveNewsToggle(@PathVariable("id") long id) {
        NewsArticle updated = newsArticleService.approveNewsToggle(id);
        if (updated==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @PutMapping("/disable/{id}")
    public ResponseEntity<NewsArticle> disableNewsToggle(@PathVariable("id") long id) {
        NewsArticle updated = newsArticleService.disableNewsToggle(id);
        if (updated==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }
//    @GetMapping(path = "ads")
//    public ResponseEntity<List<NewsArticle>> findAllAds()
//    {       List<NewsArticle> newsArticles = newsArticleService.findAllAds();
//        return ResponseEntity.ok(newsArticles);
//    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('EDITOR')")
    public ResponseEntity<NewsArticle> delete(@PathVariable("id") Long id) {
        NewsArticle newsArticle = newsArticleService.findById(id);
        if (newsArticle == null) {
            return ResponseEntity.notFound().build();
        }
        newsArticleService.delete(newsArticle);
        return ResponseEntity.ok(newsArticle);
    }

}