package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exception.MyCustomException;
import com.example.demo.response.ResponseHandler;
import com.example.demo.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/auth")
public class AuthController {
	@Autowired
	private AuthService authService;
	@Autowired
	private ResponseHandler responseHandler;

	@PostMapping("/v0/register")
	public ResponseEntity<Object> registerNewAccount(@Valid @RequestBody UserDto userDto)
			throws MethodArgumentNotValidException, MyCustomException {

		if (userDto.getEmail().isEmpty()) {
			throw new MethodArgumentNotValidException(null, null);
		}

		User user = new User();
		user.setLoginName(userDto.getLoginName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		authService.createdNewUser(user);
		return responseHandler.createdSuccessResponse();
	}

	@PutMapping("/v0/login")
	public ResponseEntity<Object> login(@Valid @RequestBody UserDto userDto)
			throws MyCustomException, MethodArgumentNotValidException {
		if (userDto.getPassword().isEmpty() || userDto.getLoginName().isEmpty()) {
			throw new MethodArgumentNotValidException(null, null);
		}
		return responseHandler.createdSuccessResponse(authService.login(userDto), 0);
	}

	@PostMapping("/v0/logout")
	public ResponseEntity<Object> logout(@RequestBody User user) throws MyCustomException {
		return responseHandler.createdSuccessResponse(authService.createdNewUser(user), 0);

	}
}
