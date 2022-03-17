package com.bits.library.service.Implementation;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.bits.library.model.IssueBookDTO;
import com.bits.library.service.IssueService;
import com.bits.library.util.MQUtility;

@Service
public class IssueServiceImpl implements IssueService {

	private static final Logger LOGGER = LogManager.getLogger(IssueServiceImpl.class);

	@Override
	public void issueBook(IssueBookDTO issueBook) {
		System.out.println(issueBook);
		notifyUser("Book Issued: " + issueBook.getBookISBN() + " *on* " + issueBook.getIssuedOn() + " *for (days)* "
				+ issueBook.getIssuedForDays() + " *to* " + issueBook.getIssuedTo());

	}

	@Override
	public IssueBookDTO fetchIssueBookDetails(Integer issueId) {
		LOGGER.info("fetchIssueBookDetails in Service for issueId: " + issueId);
		return new IssueBookDTO(10, "asd123", "TAR001", new Date(), Integer.valueOf(10));
	}

	@Override
	public List<Integer> searchIssuedBookWithStudentId(Integer studentId) {

		LOGGER.info("searchIssueBookId in Service for studentId: " + studentId);
		return (List<Integer>) Arrays.asList(123, 111, 222, 333);
	}

	private void notifyUser(String message) {
		LOGGER.info("notifying user...");
		try {
			MQUtility.sendMessageAsync(message);
		} catch (Exception e) {
			LOGGER.error("Error occured while sending message to Queue" + e);
		}
	}

}
