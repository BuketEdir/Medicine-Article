package com.api.article.medicinearticle.model.RequestModelDTO;

import lombok.Data;

import java.util.List;

@Data
public class AuthorRequestModelDTO {
    private String authorName;

    private List<Integer> articleList;

}
