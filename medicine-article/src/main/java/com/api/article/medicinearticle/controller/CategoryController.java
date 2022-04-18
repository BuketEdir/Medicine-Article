package com.api.article.medicinearticle.controller;

import com.api.article.medicinearticle.model.Article;
import com.api.article.medicinearticle.model.Category;
import com.api.article.medicinearticle.model.RequestModelDTO.CategoryRequestModelDTO;
import com.api.article.medicinearticle.repository.ArticleRepository;
import com.api.article.medicinearticle.repository.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ArticleRepository articleRepository;

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getAllCategory(@RequestParam(required = false) String categoryName) {
        try {
            List<Category> categoryList = new ArrayList<>();
            if (categoryName == null)
              categoryList=categoryRepository.findAll();

                //  categoryRepository.findAll().forEach(categoryList::add);
            else
                categoryRepository.findByCategoryName(categoryName).forEach(categoryList::add);
            if (categoryList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(categoryList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/category")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequestModelDTO categoryRequestModelDTO ) {
        try {
            Category category=new Category();


            //tek tek set get yerine kullandık
            BeanUtils.copyProperties(categoryRequestModelDTO,category);


          /*  for (int i=0;i<categoryRequestModelDTO.getArticleList().size();i++){
                Optional<Article> article=articleRepository.findById(categoryRequestModelDTO.getArticleList().get(i));

                if (article.isPresent()){
                    category.
                }
            }*/

            categoryRepository.save(category);
            return new ResponseEntity<Category>(category, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/category")
    public ResponseEntity<Category> updateCategory(@RequestParam int id, @RequestBody Category category) {
        Optional<Category> categoryData = categoryRepository.findById(id);
        if (categoryData.isPresent()) {
            Category categories = categoryData.get();
            categories.setCategoryName(category.getCategoryName());
            return new ResponseEntity<>(categoryRepository.save(categories), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @DeleteMapping("/category/{id}")
    public ResponseEntity<HttpStatus> deleteAllCategory() {
        try {
            categoryRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Category> getCategoryById(@RequestParam Integer id) {
        Optional<Category> categoryData = categoryRepository.findById(id);
        if (categoryData.isPresent()) {
            return new ResponseEntity<>(categoryData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/category")
    public ResponseEntity<Category> deleteById(@RequestParam Integer id) {
        try {
            categoryRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);


        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }




}
