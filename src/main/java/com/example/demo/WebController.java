package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("api/user")
public class WebController {

	@Autowired
	private UserService userService;

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
			@RequestParam(name = "name", required = false, defaultValue = "") String name,
			// Model là một object của Spring Boot, được gắn vào trong mọi request.
			Model model) {
		// Gắn vào model giá trị name nhận được
		model.addAttribute("name", name);

		return "hello"; // trả về file hello.html cùng với thông tin trong object Model
	}

	@GetMapping("/v0/all")
	public ResponseEntity<Object> getAllUser() {
		return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
	}

	@PostMapping("/v0/new")
	public ResponseEntity<Object> createdNewUser(@RequestBody User user) {
		User newUser = userService.createdNewUser(user);
		return new ResponseEntity<>(newUser, HttpStatus.OK);
	}

	@GetMapping("/v0/{id}")
	public ResponseEntity<Object> getUserById(@PathVariable(value = "id") Integer id) {
		User currentUser = userService.getUserById(id);
		if (currentUser != null)
			return new ResponseEntity<>(currentUser, HttpStatus.OK);
		else
			return new ResponseEntity<>("Not Exist", HttpStatus.BAD_REQUEST);
	}

	/*
	 * @RequestBody nói với Spring Boot rằng hãy chuyển Json trong request body
	 * thành đối tượng Todo
	 */
	@PutMapping("/v0/{id}")
	public ResponseEntity<Object> editUser(@PathVariable(name = "id") Integer id, @RequestBody User user) {
		User currentUser = userService.getUserById(id);
		if (currentUser == null)
			return new ResponseEntity<>("Not Exist", HttpStatus.BAD_REQUEST);
		int newData = userService.editUser(id, user);
		return new ResponseEntity<>(newData, HttpStatus.OK);
	}

	@DeleteMapping("/v0/{id}")
	public ResponseEntity<Object> removeUser(@PathVariable(name = "id") Integer id) {
		User currentUser = userService.getUserById(id);
		if (currentUser == null)
			return new ResponseEntity<>("Not Exist", HttpStatus.BAD_REQUEST);
		userService.removeUser(id);
		return new ResponseEntity<>("Success" ,HttpStatus.OK);
	}

}
