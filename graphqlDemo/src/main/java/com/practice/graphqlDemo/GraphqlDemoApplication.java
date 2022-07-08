package com.practice.graphqlDemo;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class GraphqlDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphqlDemoApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(AuthorRepository authorRepository, BookRepository bookRepository){
		return args -> {
			Author josh = authorRepository.save(new Author(null,"josh AAAA"));
			Author mark = authorRepository.save(new Author(null, "Marl BBB"));
			bookRepository.saveAll(
					List.of(new Book("title1","publisher1", josh),
							new Book("title2","publisher2", mark),
							new Book("title3", "publisher3",josh)
					));
		};
	}
}
