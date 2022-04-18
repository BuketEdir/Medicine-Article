package com.api.article.medicinearticle.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name="author")
public class Author {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;

    @Column(name = "author_Name")
    private String authorName;

    /*@OneToMany(mappedBy="author",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Article> articles=new HashSet<Article>();*/



}
