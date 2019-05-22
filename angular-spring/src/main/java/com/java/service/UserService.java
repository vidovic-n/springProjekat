package com.java.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.java.model.User;

@Service
public interface UserService {
	
	public User registerUser(User user); 
	public User getUserByUsername(String username);
}
