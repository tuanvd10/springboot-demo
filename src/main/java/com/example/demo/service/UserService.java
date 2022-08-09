package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserRepository;
import com.example.demo.entity.User;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;

	public List<User> getAllUser() {
		return userRepo.findAll();
	}

	public User createdNewUser(User user) {
		return userRepo.save(user);
	}

	public User getUserById(long id) {
		return userRepo.findById(id).orElse(null);
	}

	public int editUser(int id, User newUser) {
		int data = this.userRepo.editUserQuery(id, newUser.getStamina());
		return data;
	}

	public void removeUser(long id) {
		userRepo.deleteById(id);
		return;
	};
}
