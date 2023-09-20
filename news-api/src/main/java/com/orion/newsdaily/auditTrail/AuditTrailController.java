package com.orion.newsdaily.auditTrail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


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
}
