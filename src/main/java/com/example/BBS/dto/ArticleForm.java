package com.example.BBS.dto;

import com.example.BBS.entity.Article;

// 폼데이터를 받아올 그릇
public class ArticleForm {

    private String title; // 제목 받을 필드
    private String content; // 내용 받을 필드

    public ArticleForm(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "AticleForm{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    // DTO -> Entity
    public Article toEntity() {
        return new Article(null, title, content);
    }
}
