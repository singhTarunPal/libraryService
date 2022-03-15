package com.bits.library.service;

import java.util.List;

import com.bits.library.model.IssueBookDTO;

public interface IssueService {
	void issueBook(IssueBookDTO issueBook);
	IssueBookDTO fetchIssueBookDetails(Integer issueId);
	List<Integer> searchIssuedBookWithStudentId(Integer studentId);
	
}