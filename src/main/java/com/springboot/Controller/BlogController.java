package com.springboot.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Blog")
public class BlogController {

	@GetMapping("/allblogs")
	public String viewBlog() {
		return "allblogs";
	}
}
