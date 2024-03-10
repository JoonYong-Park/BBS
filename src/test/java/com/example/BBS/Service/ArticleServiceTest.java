package com.example.BBS.Service;

import com.example.BBS.dto.ArticleForm;
import com.example.BBS.entity.Article;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 해당 클래스는 스프링부트와 연동되어 테스팅된다.
class ArticleServiceTest {

    @Autowired // DI
    ArticleService articleService;

    @Test
    void index() {
        // 예상
        Article a = new Article(1L, "1", "1111");
        Article b = new Article(2L, "2", "2222");
        Article c = new Article(3L, "3", "3333");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a, b, c));
        // 실제
        List<Article> articles = articleService.index();
        // 비교
        assertEquals(expected.toString(), articles.toString());
    }


    @Test
    void show_성공____존재하는_id_입력() {
        // 예상
        Long id = 1L;
        Article expected = new Article(id, "1", "1111");
        // 실제
        Article article = articleService.show(id);
        // 비교
        assertEquals(expected.toString(), article.toString());
    }
    @Test
    void show_실패____존재하지_않는_id_입력() {
        // 예상
        Long id = -1L;// 없는값 입력
        Article expected = null; // 예상값 NULL
        // 실제
        Article article = articleService.show(id);
        // 비교
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void create_성공____title과_content만_있는_dto_입력() {
        // 예상
        String title = "4";
        String content = "4444";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(4L, title, content);
        // 실제
        Article article = articleService.created(dto);
        // 비교
        assertEquals(expected.toString(), article.toString());
    }
    @Test
    @Transactional
    void create_실패____id가_포함된_dto_입력() {
        // 예상
        String title = "4";
        String content = "4444";
        ArticleForm dto = new ArticleForm(4L, title, content);
        Article expected = null;
        // 실제
        Article article = articleService.created(dto);
        // 비교
        assertEquals(expected, article);
    }
}
