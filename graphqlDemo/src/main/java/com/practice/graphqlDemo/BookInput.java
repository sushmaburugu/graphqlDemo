package com.practice.graphqlDemo;

import lombok.Data;

@Data
public class BookInput {
    Long id;
    String title;
    String publisher;
    Long authorId;
}