package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.dao.AuthRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.dto.AuthDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.AccountSession;
import com.example.demo.entity.User;
import com.example.demo.exception.AuthException;
import com.example.demo.exception.MyCustomException;
import com.example.demo.jwtsecure.CustomUserDetails;
import com.example.demo.jwtsecure.JwtTokenProvider;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private AuthRepository authRepo;

	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider tokenProvider;

	public User getUserByLoginName(String loginName) {
		return userRepo.getUserByLoginName(loginName);
	}

	public User createdNewUser(User user) throws MyCustomException {
		User currentUser = userRepo.getUserByLoginName(user.getLoginName());
		if (currentUser == null)
			return userRepo.save(user);
		else
			throw AuthException.ALREADY_EXIST.getException();
	}

	public Object login(UserDto userDto) throws MyCustomException {
		User currentUser = userRepo.getUserByLoginNameAndPassword(userDto.getLoginName(), userDto.getPassword());
		if (currentUser != null) {
			// Xác thực từ username và password.

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userDto.getLoginName(), userDto.getPassword()));

			// Nếu không xảy ra exception tức là thông tin hợp lệ
			// Set thông tin authentication vào Security Context
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// Trả về jwt cho người dùng.
			String accessToken = tokenProvider.generateToken(new CustomUserDetails(currentUser));

			AccountSession accountSesion = new AccountSession();
			accountSesion.setAccessToken(accessToken);
			accountSesion.setUser(currentUser);
			authRepo.save(accountSesion);
			// authRepo.findAccountSesionByToken(accessToken);
			return new AuthDto(accessToken, currentUser.getId(), currentUser.getRole().getId());
		} else
			throw AuthException.PARAM_WRONG.getException();
	}
}
