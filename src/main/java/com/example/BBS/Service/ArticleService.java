package com.example.BBS.Service;

import com.example.BBS.dto.ArticleForm;
import com.example.BBS.entity.Article;
import com.example.BBS.repository.ArticleRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    // 트랜젝션(롤백 맛보기)
    @Transactional // 해당 메소드를 트랜젝션으로 묶는다.(모두 성공하거나, 모두 실패하거나 - 롤백)
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // dto 묶음을 entity 묶음으로 변환
        List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());

        // 위 코드를 for문 으로 작성 하면
        /*List<Article> articleList = new ArrayList<>();
        for (int i = 0; i < dtos.size(); i++) {
            ArticleForm dto = dtos.get(i);
            Article article = dto.toEntity();
            articleList.add(article);
        }*/

        // entity 묶음을 DB에 저장
        articleList.stream()
                .forEach(article -> articleRepository.save(article));
        // 위 코드를 for문 으로 작성 하면
        /*for (int i = 0; i < articleList.size(); i++) {
            Article article = articleList.get(i);
            articleRepository.save(article);
        }*/

        // 강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("강제 예외 발생!")
        );

        // 결과값 반환
        return articleList;
    }
}
