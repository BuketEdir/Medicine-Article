package com.api.article.medicinearticle.model.RequestModelDTO;

import com.api.article.medicinearticle.model.Article;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class AuthorResponseModelDTO {

    private String authorName;

    private Set<Article> articles;
    private int articleCount;

}
