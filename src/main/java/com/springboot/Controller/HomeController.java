package com.springboot.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.springboot.Entity.User;
import com.springboot.Service.UserServiceImp;

import jakarta.servlet.http.HttpServletRequest;
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
	public String saveUser(@ModelAttribute User user, HttpSession session, HttpServletRequest request) {
		if (user.getName().isEmpty() || user.getName().length() == 0 || user.getEmail().isEmpty()
				|| user.getEmail().length() == 0 || user.getPassword().isEmpty() || user.getPassword().length() == 0) {
			session.setAttribute("msg", "Some input field is empty..");
		}else {
			if(serviceImp.existsByEmail(user.getEmail())) {
				session.setAttribute("msg", "Email already exist...");
			}else {
				String url = request.getRequestURL().toString();
				url = url.replace(request.getServletPath(),"");
				
				User saveUser = serviceImp.saveUser(user,url);
				if(saveUser != null) {
					session.setAttribute("msg", "Register Successful..");
				}else {
					session.setAttribute("msg", "Something went wrong");
				}
			}
		}
		return "redirect:/register";
	}
	
	@GetMapping("/verify")
	public String verifyAccount(@Param("code") String code, Model model) {
		boolean f = serviceImp.verfiyAccount(code);
		if(f) {
			model.addAttribute("message","Account Verfied Successfully");
		}else {
			model.addAttribute("message", "Something went wrong..");
		}
		return "message";
	}
}
