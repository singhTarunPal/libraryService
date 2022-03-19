package com.bits.library.repository;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bits.library.entity.BookIssueDetails;


public interface BookIssueRepository extends CrudRepository<BookIssueDetails, Integer> {
	
	static final Logger LOGGER = LogManager.getLogger(BookIssueRepository.class);
	
	@Query("SELECT bookIssue FROM BookIssueDetails bookIssue WHERE bookIssue.issuedTo = ?1")
	List<BookIssueDetails> findByStudentId(String studentId);
	
}
