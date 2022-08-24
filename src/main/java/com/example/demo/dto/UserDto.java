package com.example.demo.dto;

import com.example.demo.customannotation.PhonenumberType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

	@PhonenumberType
	private String phoneNumber;
}
