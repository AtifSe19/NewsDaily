package com.orion.newsdaily.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orion.newsdaily.auditTrail.AuditTrail;
import com.orion.newsdaily.comment.Comment;
import com.orion.newsdaily.newsArticle.NewsArticle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Entity
@Table(name="users")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String email;
    @NonNull
    private String role;
    @NonNull
    @Column(name = "is_logged_in")
    private boolean isLoggedIn;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<NewsArticle> newsArticles;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> comments;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AuditTrail> auditTrails;
}
