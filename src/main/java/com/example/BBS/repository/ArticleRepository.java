package com.example.BBS.repository;


import com.example.BBS.entity.Article;
import org.springframework.data.repository.CrudRepository;

// 레파지토리를 직접 구현할수도 있지만 JPA에서 제공하는 레파지도리 인터페이스를 활용함
public interface ArticleRepository extends CrudRepository<Article, Long> {


}
