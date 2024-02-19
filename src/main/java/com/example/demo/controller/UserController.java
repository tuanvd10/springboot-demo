package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.exception.MyCustomException;
import com.example.demo.exception.MyUserException;
import com.example.demo.jwtsecure.CustomUserDetails;
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
	public ResponseEntity<Object> getAllUser() {
		return responseHandler.createdSuccessResponse(userService.getAllUser(), 0);
	}

	@GetMapping("/v0/{id}")
	public ResponseEntity<Object> getUserById(@PathVariable(value = "id") Integer id) throws MyCustomException {
		System.out.println("Jump to controller");
		// user can see own data only but role_admin also can see all
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (userDetails.getUser().getId().intValue() == id
				|| userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			User currentUser = userService.getUserById(id);
			if (currentUser != null)
				return responseHandler.createdSuccessResponse(currentUser, 0);
		}
		throw MyUserException.NOT_EXIST.getException();
	}

	@GetMapping("/v0/me")
	public ResponseEntity<Object> getCurrentUser() {
		// get data save in authen context => userdetails object
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return responseHandler.createdSuccessResponse(userDetails, 0);
	}

	/*
	 * @RequestBody nói với Spring Boot rằng hãy chuyển Json trong request body
	 * thành đối tượng User
	 */
	@PutMapping("/v0/{id}")
	public ResponseEntity<Object> editUser(@PathVariable(name = "id") Integer id, @RequestBody User user)
			throws MyCustomException {
		// user can see own data only but role_admin also can see all
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (userDetails.getUser().getId().intValue() == id
				|| userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			User currentUser = userService.getUserById(id);
			if (currentUser != null) {
				int newData = userService.editUser(id, user);
				return responseHandler.createdSuccessResponse(newData, 0);
			}
		}
		throw MyUserException.NOT_EXIST.getException();
	}

	@DeleteMapping("/v0/{id}")
//	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Object> removeUser(@PathVariable(name = "id") Integer id) throws MyCustomException {
		User currentUser = userService.getUserById(id);
		if (currentUser == null)
			throw MyUserException.NOT_EXIST.getException();
		userService.removeUser(id);
		return responseHandler.createdSuccessResponse("Success", 0);
	}
}
