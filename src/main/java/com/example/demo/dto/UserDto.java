package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {

	private long id;

	// user name should not be null or empty
	// user name should have at least 2 characters
	@NotEmpty(message = "loginName cannot empty")
	@Size(min = 6, message = "user name should have at least 6 characters")
	private String loginName;

	// email should be a valid email format
	// email should not be null or empty
	// @NotEmpty(message = "email cannot empty")
	@Email(message = "email is not right format")
	private String email;

	// password should not be null or empty
	// password should have at least 8 characters
	@NotEmpty(message = "password cannot empty")
	@Size(min = 8, message = "password should have at least 8 characters")
	private String password;

	public UserDto() {

	}

	public UserDto(String loginName, String email, String password) {
		super();
		this.loginName = loginName;
		this.email = email;
		this.password = password;
	}
}
