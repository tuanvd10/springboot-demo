package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserRepository;
import com.example.demo.entity.User;
import com.example.demo.jwtsecure.CustomUserDetails;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepository userRepo;

	public List<User> getAllUser() {
		return userRepo.findAll();
	}

	public User getUserById(long id) {
		return userRepo.findById(id).orElse(null);
	}

	public int editUser(int id, User newUser) {
		int data = this.userRepo.editUserQuery(id, newUser.getPassword());
		return data;
	}

	public void removeUser(long id) {
		userRepo.deleteById(id);
		return;
	};

	@Override
	public UserDetails loadUserByUsername(String username) {
		// Kiểm tra xem user có tồn tại trong database không?
		User user = userRepo.getUserByLoginName(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new CustomUserDetails(user);
	}

	public UserDetails loadUserById(Long userId) {
		// Kiểm tra xem user có tồn tại trong database không?
		User user = userRepo.findById(userId).orElse(null);

		if (user == null) {
			throw new UsernameNotFoundException(userId.toString());
		}
		return new CustomUserDetails(user);
	}
}
