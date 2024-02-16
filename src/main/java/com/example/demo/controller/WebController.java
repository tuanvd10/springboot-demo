package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class WebController {

	@GetMapping("/")
	public String index() {
		return "index.html";
	}

	@GetMapping("/about") // Nếu người dùng request tới địa chỉ "/about"
	public String about() {
		return "about"; // Trả về file about.html
	}

	@GetMapping("/hello")
	public String hello(
			// Request param "name" sẽ được gán giá trị vào biến String
			@RequestParam(name = "name", required = false, defaultValue = "anymous") String name,
			// Model là một object của Spring Boot, được gắn vào trong mọi request.
			Model model) throws Exception {
		// Gắn vào model giá trị name nhận được
		model.addAttribute("name", name);
		return "hello"; // trả về file hello.html cùng với thông tin trong object Model
	}
}
