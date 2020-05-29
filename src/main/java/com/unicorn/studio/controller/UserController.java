package com.unicorn.studio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import com.unicorn.studio.entity.User;
import com.unicorn.studio.service.UnicornService;

@Controller
@RequestMapping("/users")
public class UserController {
	// Inject Unicorn Service
	@Autowired
	private UnicornService unicornService;
	
	@RequestMapping("/list")
	public String listUser(Model model) {
		List<User> users = unicornService.getUsers();
		model.addAttribute("theUsers", users);
		return "list-users";
	}
	
	@GetMapping("/new-user-form")
	public String newUser(Model model) {
		User user = new User();
		model.addAttribute("theUser",user);
		return "user-form";
	}
	
	@PostMapping("/add-user")
	public String addUser(@ModelAttribute("theUser") User user) {
		unicornService.saveUser(user);
		return "redirect:/user/list";
	}
	
	@GetMapping("/edit-user")
	public String editUser(@RequestParam("userID") int id, Model model) {
		User user = unicornService.getUser(id);
		model.addAttribute("theUser", user);
		return "user-form";
	}
	
	@PostMapping("/delete")
	public String deleteUser(@RequestParam("userID") int id, Model model) {
		unicornService.deleteUser(id);
		return "redirect:/user/list";
	}
}
