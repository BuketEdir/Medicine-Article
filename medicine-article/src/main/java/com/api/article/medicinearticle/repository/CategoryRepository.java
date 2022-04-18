package com.api.article.medicinearticle.repository;

import com.api.article.medicinearticle.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    List<Category> findByCategoryName(String categoryName);

}
