package com.example.BBS.controller;

import com.example.BBS.dto.ArticleForm;
import com.example.BBS.entity.Article;
import com.example.BBS.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@Slf4j // 로깅 기능을 사용할 수 있는 어노테이션
public class ArticleController {

    // 레파지토리를 주입받음
    @Autowired // 스프링부트가 미리 생성해놓은 객체를 가져다가 자동 연결!(DI)
    private ArticleRepository articleRepository; // 데이터를 가져오는 역할

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
        return "redirect:/articles/" + saved.getId();
    }

    // 데이터 조회하기
    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id = " + id);

        // 1. DB에서 id에 맞는 데이터를 레파지토리로 가져오기
         Article articleEntity = articleRepository.findById(id).orElse(null); // 자바 8부터 추가된 Optional 클래스

        // 2. 가져온 데이터를 모델에 등록
        model.addAttribute("article", articleEntity); // article이라는 이름으로 articleEntity를 등록

        //3. view page
        return "articles/show";
    }

    // 목록 조회
    @GetMapping("/articles")
    public String index(Model model) {
        // 1. DB에서 모든 데이터를 레파지토리로 가져오기
        List<Article> articleEntityLst = articleRepository.findAll();

        // 2. 가져온 데이터를 모델에 등록
        model.addAttribute("articleList", articleEntityLst);

        // 3. view page
        return "articles/index";
    }

    // 수정 폼 보여주기
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        // 1. 수정할 데이터를 레파지토리를 통해 Article 객체로 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 2. 가져온 데이터를 모델에 등록
        model.addAttribute("article", articleEntity);

        return "articles/edit";
    }

    // 수정하기
    @PostMapping("/articles/update")
    public String update(ArticleForm form) {
        log.info(form.toString());

        // 1. DTO -> (Controller) -> Entity
        Article articleEntity = form.toEntity();

        // 2. Entity -> (repository) -> DB
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        if (target != null) {
            articleRepository.save(articleEntity);
        }

        // 3. view page(redirect)
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){ // RedirectAttributes : 리다이렉트 시 데이터를 전달하는 용도
        log.info("삭제요청이 들어왔습니다.");

        // 1. 삭제할 데이터를 레파지토리를 통해 Article 객체로 가져오기
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());

        // 2. 대상을 삭제 한다.
        if(target != null){
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제가 완료되었습니다.");
        }

        // 3. view page(redirect)
        return "redirect:/articles";
    }








}