package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.exception.MyCustomException;
import com.example.demo.exception.MyUserException;
import com.example.demo.response.ResponseHandler;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("api/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private ResponseHandler responseHandler;

	@GetMapping("/v0/all")
	@ResponseBody
	public ResponseEntity<Object> getAllUser() {
		return responseHandler.createdSuccessResponse(userService.getAllUser(), 0);
	}

	@GetMapping("/v0/{id}")
	@ResponseBody
	public ResponseEntity<Object> getUserById(@PathVariable(value = "id") Integer id) throws MyCustomException {
		User currentUser = userService.getUserById(id);
		if (currentUser != null)
			return responseHandler.createdSuccessResponse(currentUser, 0);
		else
			throw MyUserException.NOT_EXIST.getException();
	}

	/*
	 * @RequestBody nói với Spring Boot rằng hãy chuyển Json trong request body
	 * thành đối tượng User
	 */
	@PutMapping("/v0/{id}")
	public ResponseEntity<Object> editUser(@PathVariable(name = "id") Integer id, @RequestBody User user)
			throws MyCustomException {
		User currentUser = userService.getUserById(id);
		if (currentUser == null)
			throw MyUserException.NOT_EXIST.getException();
		int newData = userService.editUser(id, user);
		return responseHandler.createdSuccessResponse(newData, 0);
	}

	@DeleteMapping("/v0/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Object> removeUser(@PathVariable(name = "id") Integer id) throws MyCustomException {
		User currentUser = userService.getUserById(id);
		if (currentUser == null)
			throw MyUserException.NOT_EXIST.getException();
		userService.removeUser(id);
		return responseHandler.createdSuccessResponse("Success", 0);
	}
}
