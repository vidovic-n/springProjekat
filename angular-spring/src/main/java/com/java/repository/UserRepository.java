package com.java.repository;

import org.springframework.data.repository.CrudRepository;

import com.java.model.User;

public interface UserRepository  extends CrudRepository<User, Long>{
	
	User findByUsername(String username);

}
