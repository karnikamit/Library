package io.springworks.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import io.springworks.models.User;
import io.springworks.repos.UserRepo;

@Service
public class UserService {

	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	UserRepo userRepo;

	protected int userId = 000;

	public List<User> getUser(int id){
		List<User> users = new ArrayList<>();
		Optional<User> user = userRepo.findById(id);
		if(user.isPresent()) {
			users.add(user.get());
			logger.info("Found user with id - {}", id);
			return users;
		}
		logger.error("User with id - {} does not exist!", id);
		return users;
	}

	public int addUser(User user) {
		if(user == null) {
			logger.error("Invalid User object to add!");
			return -1;
		}
		userId += 1;
		user.setId(userId);
		userRepo.save(user);
		logger.info("Sucessfully updated the record with new User: ID - {}, Name - {}", userId, user.getName());
		return userId;
	}

	public Boolean deleteUser(int id) {
		List<User> users = getUser(id);
		if(CollectionUtils.isEmpty(users)) {
			logger.error("User - {} not found!");
			return Boolean.FALSE;
		}
		logger.info("Removing user {} from record.", id);
		userRepo.delete(users.get(0));
		return Boolean.TRUE;
	}
}
