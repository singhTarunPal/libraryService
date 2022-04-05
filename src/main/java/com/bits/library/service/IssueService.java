package com.bits.library.service;

import java.util.List;

import com.bits.library.model.IssueBookDTO;
import com.bits.library.model.IssuedBookDetailsDTO;
import com.bits.library.model.ReturnBookDTO;

public interface IssueService {
	String issueBook(IssueBookDTO issueBook);
	IssuedBookDetailsDTO fetchIssueBookDetails(Integer issueId);
	List<IssuedBookDetailsDTO> searchIssuedBookWithStudentId(String studentId);
	String returnBook(ReturnBookDTO issueBook);	
}
