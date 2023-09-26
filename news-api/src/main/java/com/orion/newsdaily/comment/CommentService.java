package com.orion.newsdaily.comment;

import com.orion.newsdaily.newsArticle.NewsArticle;
import com.orion.newsdaily.newsArticle.NewsArticleRepo;
import com.orion.newsdaily.user.User;
import com.orion.newsdaily.user.UserRepo;
import com.orion.newsdaily.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepo commentRepo;
    private final UserRepo userRepo;
    private final UserService userService;
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
    public List<Comment> findPendingComments()
    {
        return commentRepo.findPendingComments();
    }
    public List<Comment> findAll() {
        return commentRepo.findAllNewsForUser();
    }

    public Comment findById(Long id) {
        return commentRepo.findById(id).orElse(null);
    }

    public Comment approveCommentToggle(long id) {
        Optional<Comment> comment = commentRepo.findById(id);
        if (comment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
        }
        Comment commentToUpdate = comment.get();
        if(commentToUpdate.getIsApproved().equals(Boolean.FALSE)){
            commentToUpdate.setIsApproved(true);
        } else if (commentToUpdate.getIsApproved().equals(Boolean.TRUE)) {
            commentToUpdate.setIsApproved(false);

        }
        commentRepo.save(commentToUpdate);
        return commentToUpdate;
    }

    @Transactional
    public Comment disableCommentToggle(long id) {
        Optional<Comment> comment = commentRepo.findById(id);
        if (comment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
        }
        Comment commentToUpdate = comment.get();
        if(commentToUpdate.getIsDisabled().equals(Boolean.FALSE)){
            commentToUpdate.setIsDisabled(true);
        } else if (commentToUpdate.getIsDisabled().equals(Boolean.TRUE)) {
            commentToUpdate.setIsDisabled(false);
        }
        commentRepo.save(commentToUpdate);
        return commentToUpdate;
    }

    @Transactional
    public void delete(Comment comment) {
        commentRepo.delete(comment);
    }

    public List<Comment> findAllCommentsForEditor() {
        return commentRepo.findAllCommentsForEditor();
    }

    public String getUsernameFromCommentId(Long id){
        Comment comment=findById(id);
        return comment.getUser().getUsername();
    }

    public CommentDTO NewsSpecificCommentsWithAuthor(Long id) {

        List<Comment> commentList=commentRepo.NewsSpecificComments(id);


//        List<Comment> commentList=commentRepo.findAll();
        CommentDTO commentDTO = new CommentDTO();
        List<Long> commentIds = commentList.stream()
                .map(Comment::getId) // get the id of each comment
                .collect(Collectors.toList()); // collect the ids into a list
        commentDTO.setCommentId(commentIds); // set the comment id list to the DTO

        List<String> userNameList = new ArrayList<>(); // create an empty list of usernames
        for (int i=0;i<commentList.size();i++) // loop through the comment list
        {
            String username=getUsernameFromCommentId(commentDTO.getCommentId().get(i)); // get the username of each comment
            userNameList.add(username); // add the username to the list
        }
        commentDTO.setUsername(userNameList); // set the username list to the DTO
        commentDTO.setComments(commentList);
        return commentDTO; // return the DTO
    }

}