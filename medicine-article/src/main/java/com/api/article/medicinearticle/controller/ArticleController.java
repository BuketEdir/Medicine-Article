package com.api.article.medicinearticle.controller;

import com.api.article.medicinearticle.model.Article;
import com.api.article.medicinearticle.model.Author;
import com.api.article.medicinearticle.model.Category;
import com.api.article.medicinearticle.model.RequestModelDTO.ArticleRequestModelDTO;
import com.api.article.medicinearticle.model.RequestModelDTO.ArticleResponseModelDTO;
import com.api.article.medicinearticle.repository.ArticleRepository;
import com.api.article.medicinearticle.repository.AuthorRepository;
import com.api.article.medicinearticle.repository.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.apache.tomcat.jni.Mmap.delete;

@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class ArticleController {
    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AuthorRepository authorRepository;

    @GetMapping("/article")
    public ResponseEntity<List<ArticleResponseModelDTO>> getAllArticle(@RequestParam(required = false) String title) {
        try {
            List<Article> articleList = new ArrayList<>();
            List<ArticleResponseModelDTO> articleResponseModelDTOS = new ArrayList<>();
            if (title == null)
                articleList = articleRepository.findAll();

            else
                articleRepository.findByTitle(title).forEach(articleList::add);
            if (articleList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            for (int i = 0; i < articleList.size(); i++) {

                ArticleResponseModelDTO articleResponseModelDTO = new ArticleResponseModelDTO();
                BeanUtils.copyProperties(articleList.get(i), articleResponseModelDTO);
                articleResponseModelDTO.setCategoryCount(articleList.get(i).getCategorySet().size());

                articleResponseModelDTOS.add(articleResponseModelDTO);

            }



            return new ResponseEntity<>(articleResponseModelDTOS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    /*@PostMapping("/article")
    public ResponseEntity<Article> createArticle(@RequestBody ArticleRequestModelDTO articleRequestModelDTO) {
        try {
            Article article = new Article();


            article.setArticle(articleRequestModelDTO.getArticle());

            //tek tek setlemek yerine bunu kullandık.Field alanları birebir aynı olanları
            BeanUtils.copyProperties(articleRequestModelDTO, article);

            for (int i = 0; i < articleRequestModelDTO.getCategoryList().size(); i++) {

                Optional<Category> category = categoryRepository.findById(articleRequestModelDTO.getCategoryList().get(i));

                if (category.isPresent()) {
                    article.getCategorySet().add(category.get());
                }


            }




            for (int i=0;i<articleRequestModelDTO.getAuthorList().size();i++){
                Optional<Author> author=authorRepository.findById(articleRequestModelDTO.getAuthorList().get(i));
                if (author.isPresent()){
                   article.getAuthor().;
                }
            }

            articleRepository.save(article);
            return new ResponseEntity<Article>(article, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
*/

    @PutMapping("/article")
    public ResponseEntity<Article> updateArticle(@RequestParam int id, @RequestBody Article article) {
        Optional<Article> articleData = articleRepository.findById(id);
        if (articleData.isPresent()) {
            Article _article = articleData.get();


            _article.setTitle(article.getTitle());
            _article.setArticle(article.getArticle());
            _article.setDate(article.getDate());
            _article.setDescription(article.getDescription());
            _article.setCommentNumber(article.getCommentNumber());
            _article.setLikesNumber(article.getLikesNumber());
            _article.setLiked(article.isLiked());
            _article.setMarked(article.isMarked());
            _article.setImage(article.getImage());
            _article.setBanner(article.getBanner());

            return new ResponseEntity<>(articleRepository.save(_article), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/article/{id}")
    public ResponseEntity<HttpStatus> deleteAllArticle() {
        try {
            articleRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/article/{id}")
    public ResponseEntity<Article> getArticleById(@RequestParam Integer id) {
        Optional<Article> articleData = articleRepository.findById(id);
        if (articleData.isPresent()) {
            return new ResponseEntity<>(articleData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping(value = "/article")
    public ResponseEntity<Article> deleteById(@RequestParam Integer id) {
        try {
            articleRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);


        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/article/by-likes/{likesNumber}")
    public ResponseEntity<List<Article>> findAllByCreditGreaterThanEqual(@PathVariable("likesNumber") int likesNumber) {
        Optional<List<Article>> articleList = articleRepository.findAllByLikesNumberGreaterThanEqual(likesNumber);
        if (articleList.isPresent()) {

            return new ResponseEntity<List<Article>>(articleList.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


}