package com.example.BBS.api;

import com.example.BBS.Service.ArticleService;
import com.example.BBS.dto.ArticleForm;
import com.example.BBS.entity.Article;
import com.example.BBS.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController // RestAPI용 컨트롤러! JSON을 반환!
public class ArticleApiController {
///////////////////////////// RestController + (Service) + JpaRepository /////////////////////////////
    // 레파지토리를 주입받음
    @Autowired // DI
    private ArticleService articleService;

    /// GET ///
    // 전체 조회
    @GetMapping("/api/articles")
    public List<Article> index() {
        return articleService.index();
    }
    // 단권 조회
    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) {
        return articleService.show(id);
    }

    /// POST ///
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) {
        Article created = articleService.created(dto);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.CREATED).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    /// PATCH ///
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) {

        Article updated = articleService.update(id, dto);
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    /// DELETE ///
    // 단일
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {

        Article deleted = articleService.delete(id);
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.OK).body(deleted) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    // 전체
    @DeleteMapping("/api/articles")
    public ResponseEntity<Article> deleteAll() {
        articleService.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    // 트랜젝션(반드시 성공해야하는 일련의 과정) -> 실패시 롤백
    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos) { // 여러개의 게시글을 생성
        List<Article> createdList = articleService.createArticles(dtos);
        return (createdList != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


///////////////////////////// RestController(Controller, Service) + JpaRepository /////////////////////////////
//    // 레파지토리를 주입받음
//    @Autowired // DI
//    private ArticleRepository articleRepository;
//
//    /// GET ///
//    // 전체
//    @GetMapping("/api/articles")
//    public List<Article> index() {
//        return articleRepository.findAll();
//    }
//    // 단일
//    @GetMapping("/api/articles/{id}")
//    public Article show(@PathVariable Long id) {
//        return articleRepository.findById(id).orElse(null);
//    }
//
//    /// POST ///
//    @PostMapping("/api/articles")
//    public Article create(@RequestBody ArticleForm dto) {
//        Article article = dto.toEntity();
//        return articleRepository.save(article);
//    }
//
//    /// PATCH ///
//    @PatchMapping("/api/articles/{id}")
//    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) {
//
//        // 1. 수정용 엔티티 생성
//        Article article = dto.toEntity();
//        log.info("id: {}, article: {}", id, article.toString());
//
//        // 2. 대상 엔티티를 조회
//        Article target = articleRepository.findById(id).orElse(null);
//
//        // 3. 잘못된 요청 처리(대상이 없거나. id가 다른경우)
//        if (target == null || id != article.getId()) {
//            // 400 잘못된 요청
//            log.info("id: {}, article: {}", id, article.toString());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//
//        // 4. 업데이트 및 정상 응답(200)
//        target.patch(article);
//        Article updated = articleRepository.save(target);
//        return ResponseEntity.status(HttpStatus.OK).body(updated);
//    }
//
//    /// DELETE ///
//    // 단일
//    @DeleteMapping("/api/articles/{id}")
//    public ResponseEntity<Article> delete(@PathVariable Long id) {
//
//        // 1. 대상 엔티티 찾기
//        Article target = articleRepository.findById(id).orElse(null);
//
//        // 2. 잘못된 요청 처리
//        if (target == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//
//        // 3. 삭제
//        articleRepository.delete(target);
//        return ResponseEntity.status(HttpStatus.OK).body(null);
//    }
//    // 전체
//    @DeleteMapping("/api/articles")
//    public ResponseEntity<Article> deleteAll() {
//        articleRepository.deleteAll();
//        return ResponseEntity.status(HttpStatus.OK).body(null);
//    }
}
