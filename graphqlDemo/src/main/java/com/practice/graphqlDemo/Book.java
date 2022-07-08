package com.practice.graphqlDemo;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String publisher;
    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

    public Book() {
    }

    public Book(String title, String publisher) {
        this.title = title;
        this.publisher = publisher;
    }

    public Book(String title, String publisher, Author name) {
        this.title = title;
        this.publisher = publisher;
        this.author = name;
    }
}
