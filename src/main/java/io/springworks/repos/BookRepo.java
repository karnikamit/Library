package io.springworks.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import io.springworks.models.Book;

public interface BookRepo extends MongoRepository<Book, Integer>{

}
