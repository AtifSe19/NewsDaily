package com.orion.newsdaily.Comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepo repository;

    public Comment create(Comment comment) {
        return repository.save(comment);
    }
    public List<Comment> findPendingCommentsByUserId(Long id)
    {
        return repository.findPendingCommentsByUserId(id);
    }
    public List<Comment> NewsSpecificComments(Long id)
    {
        return repository.NewsSpecificComments(id);
    }
    public Comment update(Comment commentToUpdate, long id) {

        Optional<Comment> existingCommentOptional = repository.findById(id);

        if (existingCommentOptional.isEmpty()) {
            return null;
        }
        Comment previous = existingCommentOptional.get();
        previous.setStatus(true);
        repository.save(previous);
        return previous;
    }
    public List<Comment> findPendingComments()
    {
        return repository.findPendingComments();
    }
    public List<Comment> findAll() {
        return repository.findAll();
    }

    public Comment findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(long id) {
        Optional<Comment> comment = repository.findById(id);

        if (comment.isPresent()) {
            repository.deleteById(id);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }
}
