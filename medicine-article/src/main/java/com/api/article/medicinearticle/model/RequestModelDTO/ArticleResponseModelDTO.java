package com.api.article.medicinearticle.model.RequestModelDTO;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ArticleResponseModelDTO {

    private String image;
    private String banner;
    private String title;
    private String author;
    private String description;
    private String article;
    private Date date;
    private int commentNumber;
    private int likesNumber;
    private boolean isLiked;
    private boolean isMarked;
    private List<Integer> categoryList;
    private int categoryCount;



}
