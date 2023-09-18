package com.orion.newsdaily.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService service;

    //-------------------USER

    //to create a comment and save against a userId
    @PostMapping
    public ResponseEntity<Comment> create(@RequestBody Comment comment) {

        Comment created = service.create(comment);
        if (created != null) {
            return ResponseEntity.ok(created);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    //for user to view own pending comments
    @GetMapping("/{id}")
    public ResponseEntity<List<Comment>> findPendingCommentsByUserId(@PathVariable("id") Long id) {
        return  ResponseEntity.ok(service.findPendingCommentsByUserId(id));
    }

    //to show all comments under a specific news
    @GetMapping("/{newsId}/all")
    public ResponseEntity<List<Comment>> NewsSpecificComments(@PathVariable("newsId") Long id) {
        return  ResponseEntity.ok(service.NewsSpecificComments(id));
    }

    //-------------------EDITOR

    //to approve a comment by updating status
    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateStatus(@PathVariable("id") long id,@RequestBody Comment comment) {

        Comment up = service.update(comment, id);
        if (up==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(up);
    }

    //to show editor all pending comments
    @GetMapping("/pending")
    public ResponseEntity<List<Comment>> findPendingComments() {
        return  ResponseEntity.ok(service.findPendingComments());
    }

    //-------------------ADMIN

    //to show admin all the comments against news id and user id
    @GetMapping
    public ResponseEntity<List<Comment>> findAll() {
        List<Comment> comments = service.findAll();
        return ResponseEntity.ok(comments);

    }

    //for admin to delete a comment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        Comment comment = service.findById(id);
        if (comment==null) {
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }



}
