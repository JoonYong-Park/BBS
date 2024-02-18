package com.example.BBS.entity;

import jakarta.persistence.*;

@Entity // DB가 해당 객체를 인식 가능
public class Article {

    @Id     //대표값 지정
    @GeneratedValue// 1, 2, 3, ... 자동 생성 어노테이션/ (DB가 id를 자동생성)
    private Long id;

    @Column  // 컬럼으로 지정
    private String thtile;

    @Column
    private String content;

    public Article(Long id, String thtile, String content) {
        this.id = id;
        this.thtile = thtile;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", thtile='" + thtile + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
