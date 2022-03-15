package com.bits.library.service.Implementation;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bits.library.model.IssueBookDTO;
import com.bits.library.service.IssueService;

@Service
public class IssueServiceImpl implements IssueService {

	@Override
	public void issueBook(IssueBookDTO issueBook) {
		System.out.println(issueBook);

	}

	@Override
	public IssueBookDTO fetchIssueBookDetails(Integer issueId) {
		return new IssueBookDTO(10, "asd123", "TAR001", new Date(), Integer.valueOf(10));
	}

	@Override
	public List<Integer> searchIssuedBookWithStudentId(Integer studentId) {
		// TODO Auto-generated method stub
		return (List<Integer>) Arrays.asList(123, 111, 222, 333);
	}

}
