package com.api.article.medicinearticle.repository;

import com.api.article.medicinearticle.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article,Integer> {

    List<Article> findByTitle(String title);

    Optional<List<Article>> findAllByLikesNumberGreaterThanEqual(int credit);
}
