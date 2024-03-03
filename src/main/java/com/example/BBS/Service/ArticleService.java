package com.example.BBS.Service;

import com.example.BBS.dto.ArticleForm;
import com.example.BBS.entity.Article;
import com.example.BBS.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service // 서비스 선언! (서비스 객체를 스프링부트에 생성
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }


    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article created(ArticleForm dto) {
        Article article = dto.toEntity();
        if (article != null) return null;
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        // 1. 수정용 엔티티 생성
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", id, article.toString());

        // 2. 대상 엔티티를 조회
        Article target = articleRepository.findById(id).orElse(null);

        // 3. 잘못된 요청 처리(대상이 없거나. id가 다른경우)
        if (target == null || id != article.getId()) {
            // 400 잘못된 요청
            log.info("id: {}, article: {}", id, article.toString());
            return null;
        }
        // 4. 업데이트 및 정상 응답(200)
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;

    }

    public Article delete(Long id) {
        // 1. 대상 엔티티 찾기
        Article target = articleRepository.findById(id).orElse(null);

        // 2. 잘못된 요청 처리
        if (target == null) {
            return null;
        }
        articleRepository.deleteById(id);
        return target;
    }

    public void deleteAll() {
        articleRepository.deleteAll();
    }
}
