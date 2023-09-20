package com.orion.newsdaily.auditTrail;

import com.orion.newsdaily.newsArticle.NewsArticle;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/audit")
@RequiredArgsConstructor
public class AuditTrailController {

    @Autowired
    private final AuditTrailService auditTrailService;

    @PostMapping("/{newsid}")
    public ResponseEntity<AuditTrail> create(@PathVariable("newsid") long newsId, Authentication authentication) {

        auditTrailService.create(authentication,newsId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

//    @GetMapping
//    public ResponseEntity<List<AuditTrail>> findAll() {
//        List<AuditTrail> audit=auditTrailService.findAll();
//        return ResponseEntity.ok(audit);
//    }
    @GetMapping
    public ResponseEntity<Map<String, List>> getUserPreference(){
        List userPreference=auditTrailService.getUserPreferences();

        //User id
        //News id
        //News id sy Tags

        return ResponseEntity.ok(Map.of("content", userPreference));
    }
}
