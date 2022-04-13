package com.bits.library.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bits.library.model.LibraryUser;
import com.bits.library.repository.UserRepository;

@RestController
@CrossOrigin()
public class UserController {
	
	private static final Logger LOGGER = LogManager.getLogger(LibraryController.class);
	
	@Autowired
	UserRepository userRepo;
	
	@GetMapping("/api/users")
	public List<LibraryUser> showAllUsers() {
		LOGGER.info("Getting users ");
		userRepo.findAll().forEach(item -> LOGGER.info(item));
		LOGGER.info("Got user ");
		return userRepo.findAll();
    }
    
	@GetMapping("/api/user")
    public LibraryUser getUserByemail(@RequestParam String email) {
		LOGGER.info("Getting user by email: " + email);
        LibraryUser user = userRepo.findUserByEmail(email);
        LOGGER.info(user);
        return user;
    }	
}
