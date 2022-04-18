package com.api.article.medicinearticle.repository;

import com.api.article.medicinearticle.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author,Integer> {
    List<Author> findByAuthorName(String authorName);
}
