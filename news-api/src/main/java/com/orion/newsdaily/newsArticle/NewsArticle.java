package com.orion.newsdaily.newsArticle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orion.newsdaily.comment.Comment;
import com.orion.newsdaily.tag.Tag;
import com.orion.newsdaily.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="news_articles")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class NewsArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String title;

    @NonNull
    private String description;
    @NonNull
    private LocalDateTime postedAt;
    @NonNull
    private Boolean status;
    @NonNull
    private Boolean isSponsored;

    @ManyToOne
    @JoinColumn(name = "fk_user_id", nullable = false)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "news_articles", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> comments;

    @ManyToMany(mappedBy = "news_articles", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Tag> tags;
}
