package com.bits.library.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bits.library.model.IssueBookDTO;
import com.bits.library.model.IssuedBookDetailsDTO;
import com.bits.library.model.ReturnBookDTO;
import com.bits.library.model.StatusDTO;
import com.bits.library.service.IssueService;
import com.google.gson.Gson;

@RestController
@CrossOrigin()
public class LibraryController {
	
	private static final Logger LOGGER = LogManager.getLogger(LibraryController.class);
	
	@Autowired
	IssueService issueService;
	
	@PostMapping("/api/library/v1/issueBook")
	public ResponseEntity<String> issueBook(@RequestBody IssueBookDTO issueBookDTO) {
				
		return new ResponseEntity<String>(
				new Gson().toJson(new StatusDTO(issueService.issueBook(issueBookDTO))),
				HttpStatus.OK);
	}
	
	/*
	 * http://localhost:8080/library/issueBook?issueId=10
	 */
	@GetMapping("/api/library/v1/issueBook")
	public ResponseEntity<String> fetchIssueBookDetails(@RequestParam Integer issueId) {
		LOGGER.info("fetchIssueBookDetails for issueId: " + issueId);
		return new ResponseEntity<String>(
				new Gson().toJson(issueService.fetchIssueBookDetails(issueId)),
				HttpStatus.OK);		
	}
	
	/*
	 * http://localhost:8080/library/searchIssuedBook?studentId=10
	 */	
	@GetMapping("/api/library/v1/searchIssuedBook")
	public ResponseEntity<List<IssuedBookDetailsDTO>> searchIssueBookId(@RequestParam String studentId) {
		LOGGER.info("searchIssueBookId for studentId: " + studentId);
		return new ResponseEntity<List<IssuedBookDetailsDTO>>(
				issueService.searchIssuedBookWithStudentId(studentId),
				HttpStatus.OK);
	}
	
	@PostMapping("/api/library/v1/returnBook")
	public ResponseEntity<String> returnBook(@RequestBody ReturnBookDTO returnBookDTO) {
		LOGGER.info("returnBook with issueBookDTO: " + returnBookDTO);		
		return new ResponseEntity<String>(
				new Gson().toJson(new StatusDTO(issueService.returnBook(returnBookDTO))),
				HttpStatus.OK);
	}
}
