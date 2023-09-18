package com.orion.newsdaily.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orion.newsdaily.newsArticle.NewsArticle;
import com.orion.newsdaily.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@NoArgsConstructor
@Data
@AllArgsConstructor

public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String content;
    @NonNull
    private LocalDateTime postedAt;
    @NonNull
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "fk_user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "fk_news_article_id", nullable = false)
    @JsonIgnore
    private NewsArticle newsArticle;
}
