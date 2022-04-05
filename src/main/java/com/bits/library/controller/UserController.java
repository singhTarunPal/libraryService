package com.bits.library.controller;

import java.util.List;

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
	
	@Autowired
	UserRepository userRepo;
	
	@GetMapping("/api/users")
	public List<LibraryUser> showAllUsers() {
		System.out.println("Getting users ");
		userRepo.findAll().forEach(item -> System.out.println(item));
		System.out.println("Got user ");
		return userRepo.findAll();
    }
    
	@GetMapping("/api/user")
    public LibraryUser getUserByemail(@RequestParam String email) {
        System.out.println("Getting user by email: " + email);
        LibraryUser user = userRepo.findUserByEmail(email);
        System.out.println(user);
        return user;
    }
}
