package com.orion.newsdaily.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository <Comment, Long> {

    @Query(value = "SELECT * FROM comments WHERE fk_user_id = ?1 AND status = 'false'", nativeQuery = true)
    List<Comment> findPendingCommentsByUserId(Long id);

    @Query(value = "SELECT * FROM comments WHERE fk_news_article_id = ?1 AND status = 'true'", nativeQuery = true)
    List<Comment> NewsSpecificComments(Long id);

    @Query(value = "SELECT * FROM comments WHERE is_approved = false", nativeQuery = true)
    List<Comment> findPendingComments();
}
