package com.orion.newsdaily.Tag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orion.newsdaily.NewsArticle.NewsArticle;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id") // Rename the column
    private long tag_id;

    private String tagName;

    @ManyToMany(mappedBy = "tags",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<NewsArticle> newsArticles;


}
