package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.demo.exception.MyCustomException;
import com.example.demo.exception.MyUserException;
import com.example.demo.response.ResponseHandler;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("api/user")
public class WebController {

	@Autowired
	private UserService userService;
	@Autowired
	private ResponseHandler responseHandler;
	
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
			Model model) throws Exception {
		// Gắn vào model giá trị name nhận được
		model.addAttribute("name", name);
		return "hello"; // trả về file hello.html cùng với thông tin trong object Model
	}

	@GetMapping("/v0/all")
	public ResponseEntity<Object> getAllUser() {
		return responseHandler.createdSuccessResponse(userService.getAllUser(), 0);
	}

	@PostMapping("/v0/new")
	public ResponseEntity<Object> createdNewUser(@RequestBody User user) {
		return responseHandler.createdSuccessResponse(userService.createdNewUser(user), 0);

	}

	@GetMapping("/v0/{id}")
	public ResponseEntity<Object> getUserById(@PathVariable(value = "id") Integer id) throws MyCustomException {
		User currentUser = userService.getUserById(id);
		if (currentUser != null)
			return responseHandler.createdSuccessResponse(currentUser, 0);
		else
			throw MyUserException.NOT_EXIST.getException();
	}

	/*
	 * @RequestBody nói với Spring Boot rằng hãy chuyển Json trong request body
	 * thành đối tượng Todo
	 */
	@PutMapping("/v0/{id}")
	public ResponseEntity<Object> editUser(@PathVariable(name = "id") Integer id, @RequestBody User user) throws MyCustomException {
		User currentUser = userService.getUserById(id);
		if (currentUser == null)
			throw MyUserException.NOT_EXIST.getException();
		int newData = userService.editUser(id, user);
		return responseHandler.createdSuccessResponse(newData, 0);
	}

	@DeleteMapping("/v0/{id}")
	public ResponseEntity<Object> removeUser(@PathVariable(name = "id") Integer id) throws MyCustomException {
		User currentUser = userService.getUserById(id);
		if (currentUser == null)
			throw MyUserException.NOT_EXIST.getException();
		userService.removeUser(id);
		return responseHandler.createdSuccessResponse("Success", 0);
	}

}
