package com.revature.rideshare.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.rideshare.domain.User;
import com.revature.rideshare.service.AuthService;
import com.revature.rideshare.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	private AuthService authService;

	public void setAuthService(AuthService authService) {
		this.authService = authService;
	}

	@GetMapping("/id/{id}")
	public @ResponseBody User getUser(@PathVariable(value = "id") long id) {
		return userService.getUser(id);
	}

	@GetMapping
	public List<User> getAll() {
		return userService.getAll();
	}

	@PostMapping("/addUser")
	public void addUser(@RequestBody User user) {
		userService.addUser(user);
	}

	@PostMapping("/removeUser")
	public void removeUser(@RequestBody User user) {
		userService.removeUser(user);
	}

	@RequestMapping("/me")
	public User getCurrentUser(@RequestHeader(name = "X-JWT-RIDESHARE") String token) {
		return authService.getUserFromToken(token);
	}
	
	@PostMapping("/updateCurrentUser")
	public void updateUser(@RequestHeader(name = "X-JWT-RIDESHARE") String token, 
			@RequestBody User user) {
		userService.updateUser(user);
	}
	
	public User getUser(String jsonString) {
		try {
			return (User) new ObjectMapper().readValue(jsonString, User.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
