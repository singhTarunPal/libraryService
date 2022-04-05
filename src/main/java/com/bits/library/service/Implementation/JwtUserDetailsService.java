package com.bits.library.service.Implementation;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bits.library.model.LibraryUser;
import com.bits.library.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Value("${jwt.username}")
	private String uname;
	
	@Value("${jwt.password}")
	private String pwd;
	
	@Autowired
	UserRepository userRepo;
	
	private static final Logger LOGGER = LogManager.getLogger(JwtUserDetailsService.class);

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.info("Load username: " + username);
		LOGGER.info("Load uname: " + uname);
		LOGGER.info("Load pwd: " + pwd);
		
		//get credentials from user service
		LibraryUser libraryUser = userRepo.findUserByEmail(username);
		LOGGER.info("LibraryUser: " + libraryUser);
		 
		if (libraryUser !=null && libraryUser.getEmail().equalsIgnoreCase(username)) {			
			return new User(libraryUser.getEmail(), libraryUser.getPassword(),
					new ArrayList<>());
		}else if(uname.equalsIgnoreCase(username)) {
			//return new User("inLibrary", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
			return new User(username, pwd,
					new ArrayList<>());
		}
		else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

}