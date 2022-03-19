package com.bits.library.service;

import java.util.List;

import com.bits.library.model.IssueBookDTO;

public interface IssueService {
	String issueBook(IssueBookDTO issueBook);
	String returnBook(IssueBookDTO issueBook);
	IssueBookDTO fetchIssueBookDetails(Integer issueId);
	List<IssueBookDTO> searchIssuedBookWithStudentId(String studentId);
	
}
