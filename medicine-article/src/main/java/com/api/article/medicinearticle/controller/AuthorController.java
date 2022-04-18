package com.api.article.medicinearticle.controller;

import com.api.article.medicinearticle.model.Article;
import com.api.article.medicinearticle.model.Author;
import com.api.article.medicinearticle.model.Category;
import com.api.article.medicinearticle.model.RequestModelDTO.*;
import com.api.article.medicinearticle.repository.ArticleRepository;
import com.api.article.medicinearticle.repository.AuthorRepository;
import org.springframework.beans.BeanUtils;
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
public class AuthorController {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    ArticleRepository articleRepository;

    @GetMapping("/author")
    public ResponseEntity<List<AuthorResponseModelDTO>> getAllAuthor(@RequestParam(required = false) String authorName) {
        try {
            List<Author> authorList = new ArrayList<>();
            List<AuthorResponseModelDTO> authorResponseModelDTOS = new ArrayList<>();
            if (authorName == null)

                authorList = authorRepository.findAll();

            else
                authorRepository.findByAuthorName(authorName).forEach(authorList::add);
            if (authorList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }


            for (int i = 0; i < authorList.size(); i++) {


                AuthorResponseModelDTO authorResponseModelDTO = new AuthorResponseModelDTO();
                BeanUtils.copyProperties(authorList.get(i), authorResponseModelDTO);

                //authorResponseModelDTO.setArticles(authorList.get(0).getArticles());

                //authorResponseModelDTO.getArticles().addAll(authorResponseModelDTO.getArticleList());
                authorResponseModelDTOS.add(authorResponseModelDTO);

            }

            return new ResponseEntity<>(authorResponseModelDTOS, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PostMapping("/author")
    public ResponseEntity<Author> createAuthor(@RequestBody AuthorRequestModelDTO authorRequestModelDTO) {
        try {
            Author author = new Author();


            //tek tek setlemek yerine bunu kullandık.Field alanları birebir aynı olanları
            BeanUtils.copyProperties(authorRequestModelDTO, author);
            authorRepository.save(author);


            for (int i = 0; i < authorRequestModelDTO.getArticleList().size(); i++) {

                Optional<Article> article = articleRepository.findById(authorRequestModelDTO.getArticleList().get(i));

                if (article.isPresent()) {

                    article.get().setAuthor(author);
                    articleRepository.save(article.get());
                }
            }
            return new ResponseEntity<Author>(author, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   /* @PostMapping("/author")
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        try {

            authorRepository.save(author);
            return new ResponseEntity<Author>(author, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
*/


}
