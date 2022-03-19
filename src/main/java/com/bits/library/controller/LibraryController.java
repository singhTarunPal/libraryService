package com.bits.library.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bits.library.model.IssueBookDTO;
import com.bits.library.model.StatusDTO;
import com.bits.library.service.IssueService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/library")
public class LibraryController {
	
	private static final Logger LOGGER = LogManager.getLogger(LibraryController.class);
	
	@Autowired
	IssueService issueService;
	
	@PostMapping("/issueBook")
	public ResponseEntity<String> issueBook(@RequestBody IssueBookDTO issueBookDTO) {
				
		return new ResponseEntity<String>(
				new Gson().toJson(new StatusDTO(issueService.issueBook(issueBookDTO))),
				HttpStatus.OK);
	}
	
	/*
	 * http://localhost:8080/library/issueBook?issueId=10
	 */
	@GetMapping("/issueBook")
	public ResponseEntity<String> fetchIssueBookDetails(@RequestParam Integer issueId) {
		LOGGER.info("fetchIssueBookDetails for issueId: " + issueId);
		return new ResponseEntity<String>(
				new Gson().toJson(issueService.fetchIssueBookDetails(issueId)),
				HttpStatus.OK);		
	}
	
	/*
	 * http://localhost:8080/library/searchIssuedBook?studentId=10
	 */	
	@GetMapping("/searchIssuedBook")
	public ResponseEntity<List<?>> searchIssueBookId(@RequestParam String studentId) {
		LOGGER.info("searchIssueBookId for studentId: " + studentId);
		return new ResponseEntity<List<?>>(
				issueService.searchIssuedBookWithStudentId(studentId),
				HttpStatus.OK);
	}
	
	@PostMapping("/returnBook")
	public ResponseEntity<String> returnBook(@RequestBody IssueBookDTO issueBookDTO) {
		LOGGER.info("returnBook with issueBookDTO: " + issueBookDTO);		
		return new ResponseEntity<String>(
				new Gson().toJson(new StatusDTO(issueService.returnBook(issueBookDTO))),
				HttpStatus.OK);
	}
}
