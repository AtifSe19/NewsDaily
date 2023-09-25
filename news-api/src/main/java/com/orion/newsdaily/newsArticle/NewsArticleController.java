package com.orion.newsdaily.newsArticle;

import com.orion.newsdaily.comment.Comment;
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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
public class NewsArticleController {

    @Autowired
    private final NewsArticleService newsArticleService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final NewsTagService newsTagService;
    private final Logger logger = LoggerFactory.getLogger(getClass());


    @PreAuthorize("hasAnyAuthority('REPORTER', 'EDITOR')")
    @PostMapping
    public ResponseEntity<NewsArticle> create(
            Authentication authentication,
            @RequestBody NewsArticle newsArticle,
            @RequestParam(name = "tags") String tagsParam
    ) {
        NewsArticle created = newsArticleService.create(newsArticle, authentication);
        newsTagService.addTag(created.getId(), tagsParam);
        if (created != null ) {
            return ResponseEntity.ok(created);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    //---------USER & ADMIN

    //Get all news that are approved and NOT ADS for every user !
    @GetMapping
    @PreAuthorize("hasAuthority('USER, REPORTER')")
    public ResponseEntity<List<NewsArticle>> findAll(@AuthenticationPrincipal OAuth2User principal,
                                          @RequestParam(name = "email", defaultValue = "") String email,
                                          @RequestParam(name = "name", defaultValue = "") String name)
    {

        List<NewsArticle> newsArticles = newsArticleService.findAll();
        return ResponseEntity.ok(newsArticles);
    }

    @PreAuthorize("hasAuthority('REPORTER')")
    @GetMapping("/my-pending")
    public ResponseEntity<List<NewsArticle>> findMyPendingNews(Authentication auth) {
        Long reporterId = userService.findIdByUserName(auth.getName());
        List<NewsArticle> pendingNews = newsArticleService.findMyPendingNews(reporterId);
//
//        System.out.println("List of Pending News Articles for Editor ID: " + reporterId);
//        for (NewsArticle article : pendingNews) {
//            System.out.println("News Article ID: " + article.getId());
//            System.out.println("Title: " + article.getTitle());
//            // Add more fields as needed
//            System.out.println("------------------------------");
//        }
        return ResponseEntity.ok(pendingNews);
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @GetMapping("/pending")
    public ResponseEntity<List<NewsArticle>> findPendingNews() {
        return ResponseEntity.ok((newsArticleService.findPendingNews()));
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

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('REPORTER', 'EDITOR')")
    public ResponseEntity<NewsArticle> delete(@PathVariable("id") Long id) {
        NewsArticle newsArticle = newsArticleService.findById(id);
        int res= newsTagService.deleteAllByNewsArticleId(id);
        if (newsArticle == null && res == 0) {
            return ResponseEntity.notFound().build();
        }
        newsArticleService.delete(newsArticle);
        return ResponseEntity.ok(newsArticle);
    }

    @GetMapping("/report/{id}")
    public ResponseEntity<String> getUsernameByNewsId(@PathVariable Long id) {
        NewsArticle newsArticle = newsArticleService.findById(id);
        String username = newsArticle.getUser().getUsername();
        return ResponseEntity.ok(username);
    }

    @GetMapping("/editor")
    @PreAuthorize("hasAuthority('EDITOR')")
    public ResponseEntity<List<NewsArticle>> findAllNewsForEditor() {
        List<NewsArticle> newsArticles = newsArticleService.findAllNewsForEditor();
        return ResponseEntity.ok(newsArticles);

    }

    @GetMapping("/newsByComment/{cmtId}")
    @PreAuthorize("hasAuthority('EDITOR')")
    public ResponseEntity<NewsArticle> findNewsByCommentId(@PathVariable Long cmtId) {
        NewsArticle newsArticle = newsArticleService.findNewsByCommentId(cmtId);
        if (newsArticle == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newsArticle);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('EDITOR')")
    public ResponseEntity<NewsArticle> findById(@PathVariable Long id) {
        NewsArticle newsArticle = newsArticleService.findById(id);
        if (newsArticle == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newsArticle);
    }
}
