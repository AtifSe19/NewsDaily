package com.orion.newsdaily.comment;

import com.orion.newsdaily.newsArticle.NewsArticle;
import com.orion.newsdaily.newsArticle.NewsArticleRepo;
import com.orion.newsdaily.user.User;
import com.orion.newsdaily.user.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepo commentRepo;
    private final UserRepo userRepo;
    private final NewsArticleRepo newsArticleRepo;

    @Transactional
    public Comment create(String username, Long newsId, Comment comment) {
        User user = userRepo.findByUsername(username);
        NewsArticle newsArticle = newsArticleRepo.findById(newsId).orElse(null);
        if (user == null || newsArticle == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User or news is null");
        }

        comment.setPostedAt(LocalDateTime.now());
        comment.setIsApproved(false);
        comment.setIsDisabled(false);

        comment.setUser(user);
        comment.setNewsArticle(newsArticle);

        if(user.getComments().size() == 0){
            user.setComments(List.of(comment));
        }
        else{
            user.getComments().add(comment);
        }

        if(newsArticle.getComments().size() == 0){
            newsArticle.setComments(List.of(comment));
        }
        else{
            newsArticle.getComments().add(comment);
        }
        return commentRepo.save(comment);
    }
    public List<Comment> findPendingCommentsByUserId(Long id)
    {
        return commentRepo.findPendingCommentsByUserId(id);
    }
    public List<Comment> NewsSpecificComments(Long id)
    {
        return commentRepo.NewsSpecificComments(id);
    }
    public Comment update(Comment commentToUpdate, long id) {

        Optional<Comment> existingCommentOptional = commentRepo.findById(id);

        if (existingCommentOptional.isEmpty()) {
            return null;
        }
        Comment previous = existingCommentOptional.get();
        previous.setIsApproved(true);
        commentRepo.save(previous);
        return previous;
    }
    public List<Comment> findPendingComments()
    {
        return commentRepo.findPendingComments();
    }
    public List<Comment> findAll() {
        return commentRepo.findAll();
    }

    public Comment findById(Long id) {
        return commentRepo.findById(id).orElse(null);
    }

    public Comment toggleApprovedStatus(long id) {
        Optional<Comment> existingCommentOptional=commentRepo.findById(id);

        if (existingCommentOptional.isEmpty()) {
            return null;
        }

        Comment commentToUpdate=existingCommentOptional.get();
        if(commentToUpdate.getIsApproved().equals(Boolean.FALSE)){
            commentToUpdate.setIsApproved(true);
        } else if (commentToUpdate.getIsApproved().equals(Boolean.TRUE)) {
            commentToUpdate.setIsApproved(false);

        }

        commentRepo.save(commentToUpdate);
        return commentToUpdate;
    }

    @Transactional
    public void disableCommentToggle(long id) {
        Optional<Comment> comment = commentRepo.findById(id);
        if (comment.isPresent()) {
            if(comment.get().getIsDisabled().equals(Boolean.FALSE)){
                commentRepo.disableComment(id);
            } else if (comment.get().getIsDisabled().equals(Boolean.TRUE)) {
                commentRepo.enableComment(id);
            }
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
        }
    }
}