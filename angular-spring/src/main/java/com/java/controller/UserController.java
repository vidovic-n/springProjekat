package com.java.controller;

import java.util.List;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.java.model.ResponseMessage;
import com.java.model.User;
import com.java.service.impl.UserServiceImpl;

@RestController
@RequestMapping(value= "/user")
public class UserController {
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
    
    @CrossOrigin(origins = "*")
   	@RequestMapping(value= "/register", method = RequestMethod.POST, headers ="Accept=aplplication/json",produces=MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseMessage> registerUser(@RequestBody User user) {
    	
    	User registeredUser = new User();
    	String status = "";
    	String message = "";
    	
    	if (user.getUsername() == null || user.getPassword() == null || user.getFirstname() == null || 
    			user.getLastname() == null || user.getEmail() == null
    			|| user.getUsername().trim().equals("") || user.getPassword().trim().equals("") || user.getFirstname().trim().equals("") ||
    			user.getLastname().trim().equals("") || user.getEmail().trim().equals("")) {
    		status = "FAILED";
    		message = "You must enter all fields.";
    	} else {

    		User userData = userServiceImpl.getUserByUsername(user.getUsername());
    		
    		if (userData != null ) {
    			status = "FAILED";
    			message = "Username is already exists. Please try again";
    			
    		} else {
    			
    			status = "SUCCESS";
    			message = "Registration success.";
    			
    			registeredUser = userServiceImpl.saveUser(user);

        	}
    	}
    
    	ResponseMessage responseMessage = new ResponseMessage();
    	
    	responseMessage.setUser(registeredUser);
    	responseMessage.setStatus(status);
    	responseMessage.setMessage(message);
    	
    return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
    	
    }
    
    @CrossOrigin(origins = "*")
   	@RequestMapping(value= "/login", method = RequestMethod.POST, headers ="Accept=aplplication/json",produces=MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseMessage> loginUser(@RequestBody User user) {
    	
    	User loggedInUser = new User();
    	String status = "";
    	String message = "";
    	
    	if (user.getUsername() == null || user.getPassword() == null
    			|| user.getUsername().trim().equals("") || user.getPassword().trim().equals("")) {
    		status = "FAILED";
    		message = "You must enter username and password.";
    	} else {

    		User userData = userServiceImpl.getUserByUsername(user.getUsername());
    		
    		if (userData == null || (!user.getUsername().equals(userData.getUsername())) || (!user.getPassword().equals(userData.getPassword()))) {
    			status = "FAILED";
    			message = "Incorrect username or password.";
    		} else if(user.getUsername().equals(userData.getUsername()) && user.getPassword().equals(userData.getPassword())) {
    			
    			status = "SUCCESS";
    			message = "You are logged in.";
        		
        		loggedInUser.setUsername(userData.getUsername());
        		loggedInUser.setFirstname(userData.getFirstname());
        		loggedInUser.setLastname(userData.getLastname());
        		loggedInUser.setEmail(userData.getEmail());
        		
        	}
    	}
    
    	ResponseMessage responseMessage = new ResponseMessage();
    	
    	responseMessage.setUser(loggedInUser);
    	responseMessage.setStatus(status);
    	responseMessage.setMessage(message);
    	
    	return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
    }
   	
    
	

}
