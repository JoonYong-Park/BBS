package com.example.BBS.repository;


import com.example.BBS.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

// 레파지토리를 직접 구현할수도 있지만 JPA에서 제공하는 레파지도리 인터페이스를 활용함
public interface ArticleRepository extends CrudRepository<Article, Long> {

    @Override
    ArrayList<Article> findAll(); // 기본적으로 제공되는 findAll() 메소드를 오버라이딩해서 사용합니다.
}
