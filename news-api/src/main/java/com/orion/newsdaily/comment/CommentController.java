package com.orion.newsdaily.comment;

import com.orion.newsdaily.newsArticle.NewsArticle;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //-------------------USER

    //to create a comment and save against a userId
    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Comment> create(Authentication auth, @RequestParam Long newsId, @RequestBody Comment comment) {

        Comment created = commentService.create(auth.getName(), newsId, comment);
        if (created != null) {
            return ResponseEntity.ok(created);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    //for user to view own pending comments
    @GetMapping("/{id}")
    public ResponseEntity<List<Comment>> findPendingCommentsByUserId(@PathVariable("id") Long id) {
        return  ResponseEntity.ok(commentService.findPendingCommentsByUserId(id));
    }

    //to show all comments under a specific news
    @GetMapping("/{newsId}/all")
    public ResponseEntity<List<Comment>> NewsSpecificComments(@PathVariable("newsId") Long id) {
        return  ResponseEntity.ok(commentService.NewsSpecificComments(id));
    }

    //-------------------EDITOR

    //to approve a comment by updating status
    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateStatus(@PathVariable("id") long id, @RequestBody Comment comment) {

        Comment up = commentService.update(comment, id);
        if (up==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(up);
    }

    //to show editor all pending comments
    @GetMapping("/pending")
    public ResponseEntity<List<Comment>> findPendingComments() {
        return  ResponseEntity.ok(commentService.findPendingComments());
    }

    //-------------------ADMIN

    //to show admin all the comments against news id and user id
    @GetMapping
    public ResponseEntity<List<Comment>> findAll() {
        List<Comment> comments = commentService.findAll();
        return ResponseEntity.ok(comments);

    }


    @PreAuthorize("hasAuthority('EDITOR')")
    @PutMapping("/approve/{id}")
    public ResponseEntity<Comment> toggleApprovedStatus(@PathVariable("id") long id) {

        Comment updated = commentService.toggleApprovedStatus(id);

        if (updated==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }


    @PreAuthorize("hasAuthority('EDITOR')")
    @PutMapping("/disable/{id}")
    public ResponseEntity<Void> disableCommentToggle(@PathVariable("id") long id) {
        Comment comment = commentService.findById(id);
        if (comment==null) {
            return ResponseEntity.notFound().build();
        }
        commentService.disableCommentToggle(id);
        return ResponseEntity.ok().build();
    }
}
