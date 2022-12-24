package io.springworks.repos;

import org.springframework.data.repository.CrudRepository;

import io.springworks.models.Book;

public interface BookRepo extends CrudRepository<Book, Integer>{

}
