package com.orion.newsdaily.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orion.newsdaily.AuditTrail.AuditTrail;
import com.orion.newsdaily.Comment.Comment;
import com.orion.newsdaily.NewsArticle.NewsArticle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="Users")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;
    private String email;
    private String role;
    private boolean loggedIn;

    @OneToMany(mappedBy = "Users",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<NewsArticle> newsArticles;

    @OneToMany(mappedBy = "Users",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> comments;

    @OneToMany(mappedBy = "Users",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AuditTrail> auditTrails;


}
