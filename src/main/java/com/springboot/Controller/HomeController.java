package com.springboot.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.springboot.Entity.User;
import com.springboot.Service.UserServiceImp;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	private UserServiceImp serviceImp;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute User user, HttpSession session) {
		if (user.getName().isEmpty() || user.getName().length() == 0 || user.getEmail().isEmpty()
				|| user.getEmail().length() == 0 || user.getPassword().isEmpty() || user.getPassword().length() == 0) {
			session.setAttribute("msg", "Some input field is empty..");
		}else {
			if(serviceImp.existsByEmail(user.getEmail())) {
				session.setAttribute("msg", "Email already exist...");
			}else {
				User saveUser = serviceImp.saveUser(user);
				if(saveUser != null) {
					session.setAttribute("msg", "Register Successful..");
				}else {
					session.setAttribute("msg", "Something went wrong");
				}
			}
		}
		return "redirect:/register";
	}
}
