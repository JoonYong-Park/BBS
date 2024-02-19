package com.example.BBS.controller;

import com.example.BBS.dto.ArticleForm;
import com.example.BBS.entity.Article;
import com.example.BBS.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@Slf4j // 로깅 기능을 사용할 수 있는 어노테이션
public class ArticleController {

    // 레파지토리를 주입받음
    @Autowired // 스프링부트가 미리 생성해놓은 객체를 가져다가 자동 연결!
    private ArticleRepository articleRepository;

    // 폼 페이지 보여주기
    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    // 폼 데이터 받고 DB에 저장하기
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        //System.out.println(form.toString());  --> 로깅 기능으로 대체!
        log.info(form.toString());

        // 1. DTO -> (Controller) -> Entity
        Article article = form.toEntity();
        log.info(article.toString());

        // 2. Entity -> (repository) -> DB
        Article saved = articleRepository.save(article);
        log.info(saved.toString());

        // 3. view page
        return "";
    }
}
