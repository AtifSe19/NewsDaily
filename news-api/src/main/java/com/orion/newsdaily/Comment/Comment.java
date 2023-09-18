package com.orion.newsdaily.Comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

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
    @NotNull
    private String content;
    @NotNull
    private LocalDateTime postedAt;
    @NotNull
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "fk_user_id", nullable = false)
    @JsonIgnore
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "fk_news_article_id", nullable = false)
    @JsonIgnore
    private Long newsArticleId;
}
