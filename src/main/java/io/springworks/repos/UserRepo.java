package io.springworks.repos;

import org.springframework.data.repository.CrudRepository;

import io.springworks.models.User;

public interface UserRepo extends CrudRepository<User, Integer>{
}
