package io.springworks.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.springworks.models.Response;
import io.springworks.models.User;
import io.springworks.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	protected int responseId = 000;

	@Autowired
	UserService userService;

	@GetMapping("/get")
	public Response getUser(@RequestParam("id") int id) {
		responseId += 1;
		Response response = new Response();
		logger.info("Searching user - {}", id);
		List<User> users = userService.getUser(id);
		if(!CollectionUtils.isEmpty(users)) {
			response.setIsSuccessful(Boolean.TRUE);
			response.setHttpStatus(HttpStatus.FOUND);
			response.setObjects(users);
		}else {
			response.setHttpStatus(HttpStatus.NOT_FOUND);
			response.setErrorCode(HttpStatus.NOT_FOUND.value());
		}
		return response;
	}

	@PostMapping("/add")
	public Response addUser(@RequestBody User user) {
		responseId += 1;
		Response response = new Response();
		response.setId(responseId);

		Map<String, Integer> userMap = new HashMap<>();
		if(user == null) {
			response.setFailureReason(ControllerConstants.INVALID_PAYLOAD);
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
			response.setErrorCode(HttpStatus.BAD_REQUEST.value());
			logger.error("Invalid user object received");
			return response;
		}
		logger.info("Adding User - {}", user.getName());
		userService.addUser(user);
		Map<String, Integer> userObject = new HashMap<>();
		List<Map<String, Integer>> userList = new ArrayList<>();
		userObject.put("userId", ControllerConstants.userId);
		userList.add(userObject);
		logger.info("User added with id: {}", ControllerConstants.userId);
		response.setIsSuccessful(Boolean.TRUE);
		response.setHttpStatus(HttpStatus.ACCEPTED);
//		response.setObjects(userList);
		return response;
	}

	@DeleteMapping("/delete")
	public Response deleteUser(@RequestParam("id") int id) {
		responseId += 1;
		Response response = new Response();
		logger.info("Deleting user - {} if present");
		Boolean deleted = userService.deleteUser(id);
		if(deleted) {
			response.setIsSuccessful(deleted);
			response.setHttpStatus(HttpStatus.NO_CONTENT);
		}else {
			response.setHttpStatus(HttpStatus.NOT_FOUND);
			response.setErrorCode(HttpStatus.NOT_FOUND.value());
		}
		return response;
	}
}
