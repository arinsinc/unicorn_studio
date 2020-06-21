package com.unicorn.studio.controller;

import com.unicorn.studio.exception.NotFoundException;
import com.unicorn.studio.exception.UserExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.unicorn.studio.entity.User;
import com.unicorn.studio.service.UnicornService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class UserController {
	@Autowired
	private UnicornService unicornService;

	@GetMapping("/users")
	public List<User> getUsers() {
		return unicornService.getUsers();
	}

	@GetMapping("/users/{userId}")
	public User getUser(@PathVariable long userId) {
		User user = unicornService.getUser(userId);
		if (user == null) {
			throw new NotFoundException("User not found with ID:" + userId);
		}
		return user;
	}

	@PostMapping("/users")
	public User addUser(@RequestBody User user) {
		User isUser = unicornService.getUserByEmail(user.getEmail());
		if (isUser != null) {
			throw new UserExistsException("Account with this email id already exists");
		}
		user.setId((long)0);
		unicornService.saveUser(user);
		return user;
	}

	@PutMapping("/users")
	public User updateUser(@RequestBody User user) {
		unicornService.saveUser(user);
		return user;
	}

	@DeleteMapping("/users/{userId}")
	public String deleteUser(@PathVariable long userId) {
		User isUser = unicornService.getUser(userId);
		if (isUser == null) {
			throw new NotFoundException("User not found with ID:" + userId);
		}
		unicornService.deleteUser(userId);
		return "User deleted successfully for Id:" + userId;
	}

	@GetMapping("/login")
	public String login(Model model, String error, String logout) {
		return "Login to proceed";
	}

	@GetMapping("/logout")
	public void logout(HttpServletResponse response) throws IOException {
		System.out.println("Logged out successfully");
		response.sendRedirect("/login");
	}
}
