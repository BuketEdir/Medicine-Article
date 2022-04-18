package com.api.article.medicinearticle.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Data
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "image")
    private String image;

    @Column(name = "banner")
    private String banner;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "article")
    private String article;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "date")
    private Date date;


    @Column(name = "comment")
    private int commentNumber;

    @Column(name = "likes")
    private int likesNumber;

    @Column(name = "isLiked")
    private boolean isLiked;

    @Column(name = "isMarked")
    private boolean isMarked;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "article_category",
            joinColumns = {@JoinColumn(name = "article_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    Set<Category> categorySet = new HashSet<>();


    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="author_id")
    private Author author;


}
