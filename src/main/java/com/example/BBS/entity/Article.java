package com.example.BBS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity // DB가 해당 객체를 인식 가능
@AllArgsConstructor
@NoArgsConstructor // 기본 생성자
@ToString
@Getter
public class Article {

    @Id     //대표값 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 1, 2, 3, ... 자동 생성 어노테이션/ (DB가 id를 자동생성)
    private Long id;

    @Column  // 컬럼으로 지정
    private String title;

    @Column
    private String content;

    public void patch(Article article) {
        if (article.getTitle() != null) {
            this.title = article.getTitle();
        }
        if (article.getContent() != null) {
            this.content = article.getContent();
        }
    }

/*    public Long getId() {
        return id;
    }*/



    /*// @AllArgsConstructor
    public Article(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    // @ToString
    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }*/
}
