package com.bits.library.service;

import java.util.List;

import com.bits.library.model.IssueBookDTO;
import com.bits.library.model.ReturnBookDTO;

public interface IssueService {
	String issueBook(IssueBookDTO issueBook);
	IssueBookDTO fetchIssueBookDetails(Integer issueId);
	List<IssueBookDTO> searchIssuedBookWithStudentId(String studentId);
	String returnBook(ReturnBookDTO issueBook);	
}
