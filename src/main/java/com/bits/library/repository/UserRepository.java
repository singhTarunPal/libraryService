package com.bits.library.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.bits.library.model.LibraryUser;

public interface UserRepository extends MongoRepository<LibraryUser, String> {

	@Query("{email:'?0'}")
	LibraryUser findUserByEmail(String email);

	List<LibraryUser> findAll();
}