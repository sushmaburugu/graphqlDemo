package com.practice.graphqlDemo;

import org.reactivestreams.Publisher;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Controller
public class AuthorController {
    AuthorRepository authorRepository;
    BookRepository bookRepository;

    public AuthorController(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @QueryMapping
    public Iterable<Author> authors() {
        return authorRepository.findAll();
    }

    @QueryMapping
    public Optional<Author> authorById(@Argument Long id) {
        return authorRepository.findById(id);
    }

    @MutationMapping
    Book addBook(@Argument("input") BookInput bookInput) {
        Book book = new Book();
        book.setTitle(bookInput.getTitle());
        book.setPublisher(bookInput.getPublisher());
        Author author = authorRepository.findById(bookInput.getAuthorId()).orElseThrow(() -> new IllegalArgumentException(" author id not available"));
        book.setAuthor(author);
        return bookRepository.save(book);
        /* result search :
        mutation{
            addBook(input : {title: "newly added by mutation", publisher:"Me", authorId:1})
            {title}
        }
        query{
            authors{id,name, books{id,title,publisher}}
        }*/
    }

    @SubscriptionMapping
    public Publisher<List<Book>> books() {
        return subscriber ->
                Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
                    List<Book> books = (List<Book>) bookRepository.findAll();
                    subscriber.onNext(books);
                }, 0, 2, TimeUnit.SECONDS);
    }

}
