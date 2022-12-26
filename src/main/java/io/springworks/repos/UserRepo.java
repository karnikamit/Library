package io.springworks.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import io.springworks.models.User;

public interface UserRepo extends MongoRepository<User, Integer>{
}
