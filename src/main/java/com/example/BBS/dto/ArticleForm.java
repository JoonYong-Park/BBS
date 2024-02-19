package com.example.BBS.dto;

import com.example.BBS.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

// 폼데이터를 받아올 그릇
@AllArgsConstructor
@ToString
public class ArticleForm {

    private String title;
    private String content;
/*
    // @AllArgsConstructor
    public ArticleForm(String title, String content) {
        this.title = title;
        this.content = content;
    }
    // @ToString
    @Override
    public String toString() {
        return "AticleForm{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }*/

    // DTO -> Entity
    public Article toEntity() {
        return new Article(null, title, content);
    }
}
