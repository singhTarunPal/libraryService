package com.bits.library.service.Implementation;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bits.library.dao.ws.InventoryServiceStub;
import com.bits.library.model.BookInventory;
import com.bits.library.model.IssueBookDTO;
import com.bits.library.service.IssueService;
import com.bits.library.util.MQUtility;

@Service
public class IssueServiceImpl implements IssueService {

	private static final Logger LOGGER = LogManager.getLogger(IssueServiceImpl.class);
	
	@Autowired(required=true)
	InventoryServiceStub inventoryServiceStub;

	@Override
	public Boolean issueBook(IssueBookDTO issueBook) {
		System.out.println(issueBook);
		
		Boolean transactionSuccess=false;
		try {
			List<BookInventory> bookInventory = inventoryServiceStub.getInventoryForABook(issueBook.getBookISBN());
			LOGGER.info("bookInventory: " + bookInventory);
			
			if(bookInventory!=null && bookInventory.get(0).getCount()>0) {
				try {
					//Reduce the inventory by 1
					inventoryServiceStub.saveInventoryForABook(
							new BookInventory(issueBook.getBookISBN(), bookInventory.get(0).getCount()-1 ));
					transactionSuccess=true;
				}catch(Exception e) {
					LOGGER.info("Exception occured while calling Inventory Service - POST Service " + e);
					transactionSuccess=false;
				}
			}
			
		}
		catch(Exception e) {
			LOGGER.info("Exception occured while calling Inventory Service - GET" + e);
			transactionSuccess=false;
		}
		
		if(transactionSuccess) {
			try {
				notifyUser("Book Issued: *" + issueBook.getBookISBN() + "* on *" + issueBook.getIssuedOn() + "* for (days) *"
					+ issueBook.getIssuedForDays() + " to *" + issueBook.getIssuedTo() + "*");
				transactionSuccess=true;
			}catch(Exception e) {
				LOGGER.info("Exception occured while calling Notification Service " + e);
				transactionSuccess=false;
			}		
		}else {
			LOGGER.info("Issue Book transaction has failed");
		}
		
		if(transactionSuccess) {
			try {
				//save in library DB
				transactionSuccess=true;
			}catch(Exception e) {
				LOGGER.info("Exception occured while Saving in Library DB " + e);
				transactionSuccess=false;
			}		
		}else {
			LOGGER.info("Issue Book transaction has failed");
		}
		
		return transactionSuccess;
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
