package com.bits.library.service.Implementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bits.library.dao.ws.InventoryServiceStub;
import com.bits.library.entity.BookIssueDetails;
import com.bits.library.model.BookInventory;
import com.bits.library.model.IssueBookDTO;
import com.bits.library.model.ReturnBookDTO;
import com.bits.library.repository.BookIssueRepository;
import com.bits.library.service.IssueService;
import com.bits.library.util.MQUtility;

@Service
public class IssueServiceImpl implements IssueService {

	private static final Logger LOGGER = LogManager.getLogger(IssueServiceImpl.class);

	@Autowired(required = true)
	InventoryServiceStub inventoryServiceStub;

	@Autowired
	BookIssueRepository bookIssueRepository;

	@Override
	public String issueBook(IssueBookDTO issueBook) {
		LOGGER.info("Returning book: " + issueBook);
		Boolean transactionSuccess = false;
		
		  try { 
			  List<BookInventory> bookInventory =
					  inventoryServiceStub.getInventoryForABook(issueBook.getBookISBN());
			  LOGGER.info("bookInventory: " + bookInventory);
		  
			  if(bookInventory!=null && bookInventory.get(0).getCount()>0) 
			  { 
				  try { 
					  //Reduce the inventory by 1 
					  BookInventory bookInventoryResp = inventoryServiceStub.saveInventoryForABook( new
							  BookInventory(issueBook.getBookISBN(), bookInventory.get(0).getCount()-1 ));
					  LOGGER.info("bookInventoryResp : " + bookInventoryResp);
					  transactionSuccess=true; 
					  }
				  catch(Exception e) { 
					  LOGGER.
					  	info("Exception occured while calling Inventory Service - POST Service " + e); 
					  transactionSuccess=false; 
					  } 
				  }
		 
		  }catch(Exception e) {
			  LOGGER.info("Exception occured while calling Inventory Service - GET" + e);
			  transactionSuccess=false; 
			 }
		  
		if (transactionSuccess) {
			try {
				bookIssueRepository
						.save(new BookIssueDetails(null, issueBook.getBookISBN(), issueBook.getIssuedTo(), new Date(), // Issued
																														// On
								issueBook.getIssuedForDays(), "N", null));

				transactionSuccess = true;
			} catch (Exception e) {
				LOGGER.info("Exception occured while Saving in Library DB " + e);
				transactionSuccess = false;
			}
		} else {
			LOGGER.info("Issue Book transaction has failed");
		}
		
		if(transactionSuccess) {
			 try { 
				 notifyUser("Book Issued: *" +
						 issueBook.getBookISBN() + "* on *" + issueBook.getIssuedOn() +
						 "* for (days) *" + issueBook.getIssuedForDays() + " to *" +
						 issueBook.getIssuedTo() + "*"); 
			 }catch(Exception e){
				  LOGGER.info("Exception occured while calling Notification Service " + e);
				  } 
		}else {
			  LOGGER.info("Issue Book transaction or Save in Library service has failed"); 
			  }

		if (transactionSuccess)
			return "Transaction Successful";
		else
			return "Transaction failed";
	}

	@Override
	public IssueBookDTO fetchIssueBookDetails(Integer issueId) {
		LOGGER.info("fetchIssueBookDetails in Service for issueId: " + issueId);
		IssueBookDTO issueBookDTO = null;

		Optional<BookIssueDetails> bookIssueDetailsOptional = bookIssueRepository.findById(issueId);
		if (bookIssueDetailsOptional.isPresent()) {
			issueBookDTO = new IssueBookDTO(bookIssueDetailsOptional.get().getId(),
					bookIssueDetailsOptional.get().getBookId(), bookIssueDetailsOptional.get().getIssuedTo(),
					bookIssueDetailsOptional.get().getIssuedOn(), bookIssueDetailsOptional.get().getIssuedForDays(),
					bookIssueDetailsOptional.get().getReturnedFlag(), bookIssueDetailsOptional.get().getReturnedOn());
		}
		return issueBookDTO;
	}

	@Override
	public List<IssueBookDTO> searchIssuedBookWithStudentId(String studentId) {

		LOGGER.info("searchIssueBookId in Service for studentId: " + studentId);
		List<IssueBookDTO> issueBookDTOList = new ArrayList<IssueBookDTO>();
		List<BookIssueDetails> bookIssueDetailsList = bookIssueRepository.findByStudentId(studentId);
		bookIssueDetailsList.stream().forEach(bookIssueDetailsOptional -> {

			issueBookDTOList.add(new IssueBookDTO(bookIssueDetailsOptional.getId(),
					bookIssueDetailsOptional.getBookId(), bookIssueDetailsOptional.getIssuedTo(),
					bookIssueDetailsOptional.getIssuedOn(), bookIssueDetailsOptional.getIssuedForDays(),
					bookIssueDetailsOptional.getReturnedFlag(), bookIssueDetailsOptional.getReturnedOn()));
		});
		return issueBookDTOList;
	}

	@Override
	public String returnBook(ReturnBookDTO returnBook) {
		Boolean transactionSuccess = false;
		LOGGER.info("Returning book: " + returnBook);
		
		  try { 
			  List<BookInventory> bookInventory =
					  inventoryServiceStub.getInventoryForABook(returnBook.getBookISBN());
			  LOGGER.info("bookInventory: " + bookInventory);
		  
			  if(bookInventory!=null && bookInventory.get(0).getCount()>0) { try {
				  //Decrease the inventory by 1 
				  BookInventory bookInventoryResp = inventoryServiceStub.saveInventoryForABook( new
						  BookInventory(returnBook.getBookISBN(), bookInventory.get(0).getCount()-1 ));
				  LOGGER.info("bookInventoryResp : " + bookInventoryResp);
				  transactionSuccess=true; 
			  }catch(Exception e) { 
				  LOGGER.
					  info("Exception occured while calling Inventory Service - POST Service " +
							  e); 
				  transactionSuccess=false; 
				  } 
			  }
		  
		  } catch(Exception e) {
		  LOGGER.info("Exception occured while calling Inventory Service - GET" + e);
		  transactionSuccess=false; }
		 
		if (transactionSuccess) {
			try {
				Optional<BookIssueDetails> bookIssueDetailsOptional = bookIssueRepository
						.findById(returnBook.getIssueId());
				if (bookIssueDetailsOptional.isPresent()) {
					bookIssueRepository.save(new BookIssueDetails(bookIssueDetailsOptional.get().getId(),
							bookIssueDetailsOptional.get().getBookId(), bookIssueDetailsOptional.get().getIssuedTo(),
							bookIssueDetailsOptional.get().getIssuedOn(),
							bookIssueDetailsOptional.get().getIssuedForDays(), "Y", new Date()));
				}
				transactionSuccess = true;
			} catch (Exception e) {
				LOGGER.info("Exception occured while Saving in Library DB " + e);
				transactionSuccess = false;
			}
		} else {
			LOGGER.info("Return Book transaction has failed");
		}
		
		if(transactionSuccess) {
			 try { 
				 notifyUser("Book returned: *" +
						 returnBook.getBookISBN() + "* on *" + new Date() +
						  " by *" + returnBook.getIssuedTo() + "*"); 
				 
			 }catch(Exception e){
				  LOGGER.info("Exception occured while calling Notification Service " + e);
				  } 
		}else {
			  LOGGER.info("Issue Book transaction or Save in Library service has failed"); 
			  }

		if (transactionSuccess)
			return "Transaction Successful";
		else
			return "Transaction failed";
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
