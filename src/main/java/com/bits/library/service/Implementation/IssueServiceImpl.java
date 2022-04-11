package com.bits.library.service.Implementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bits.library.dao.ws.BookServiceStub;
import com.bits.library.dao.ws.InventoryServiceStub;
import com.bits.library.entity.BookIssueDetails;
import com.bits.library.model.Book;
import com.bits.library.model.BookInventory;
import com.bits.library.model.IssueBookDTO;
import com.bits.library.model.IssuedBookDetailsDTO;
import com.bits.library.model.ReturnBookDTO;
import com.bits.library.repository.BookIssueRepository;
import com.bits.library.service.IssueService;
import com.bits.library.util.MQUtility;

@Service
public class IssueServiceImpl implements IssueService {

	private static final Logger LOGGER = LogManager.getLogger(IssueServiceImpl.class);

	@Autowired(required = true)
	InventoryServiceStub inventoryServiceStub;
	
	@Autowired(required = true)
	BookServiceStub bookServiceStub;

	@Autowired
	BookIssueRepository bookIssueRepository;

	@Override
	public String issueBook(IssueBookDTO issueBook) {
		LOGGER.info("Issuing book: " + issueBook);
		Boolean transactionSuccess = false;
		
		  try { 
			  List<BookInventory> bookInventory =
					  inventoryServiceStub.getInventoryForABook(issueBook.getBookId());
			  LOGGER.info("bookInventory: " + bookInventory);
		  
			  if(bookInventory!=null && bookInventory.get(0).getCount()>0) 
			  { 
				  try { 
					  //Reduce the inventory by 1 
					  BookInventory bookInventoryResp = inventoryServiceStub.saveInventoryForABook( new
							  BookInventory(issueBook.getBookId(), bookInventory.get(0).getCount()-1 ));
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
						.save(new BookIssueDetails(null, issueBook.getBookId(), issueBook.getIssuedToEmailId(), 
								new Date(), // Issued On
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
				 notifyUser(issueBook.getIssuedToEmailId(),"Book Issued: BookID: '" +
						 issueBook.getBookId() + "' on '" + new Date() +
						 "' for (days) '" + issueBook.getIssuedForDays() + "' to '" +
						 issueBook.getIssuedToEmailId() + "'"); 
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
	public IssuedBookDetailsDTO fetchIssueBookDetails(Integer issueId) {
		LOGGER.info("fetchIssueBookDetails in Service for issueId: " + issueId);
		IssuedBookDetailsDTO issueBookDTO = null;

		Optional<BookIssueDetails> bookIssueDetailsOptional = bookIssueRepository.findById(issueId);
		if (bookIssueDetailsOptional.isPresent()) {
			//get the book author and title
			String bookAuthor = "Not-Available";
			String bookTitle = "Not-Available";
			List<Book> books = bookServiceStub.getBookById(bookIssueDetailsOptional.get().getBookId());
			if(books !=null && books.size()>0) {
				bookAuthor=books.get(0).getAuthor();
				bookTitle=books.get(0).getTitle();
			}
			
			issueBookDTO = new IssuedBookDetailsDTO(bookIssueDetailsOptional.get().getId(),
					bookIssueDetailsOptional.get().getBookId(), bookIssueDetailsOptional.get().getIssuedTo(),
					bookIssueDetailsOptional.get().getIssuedOn(), bookIssueDetailsOptional.get().getIssuedForDays(),
					bookIssueDetailsOptional.get().getReturnedFlag(), bookIssueDetailsOptional.get().getReturnedOn(),
					bookAuthor, bookTitle);
		}
		LOGGER.info("issueBookDTO: " + issueBookDTO);
		return issueBookDTO;
	}
	
	@Override
	public List<IssuedBookDetailsDTO> fetchAllIssueBookDetails() {

		LOGGER.info("fetchAllIssueBookDetails in Service ");
		List<IssuedBookDetailsDTO> issueBookDTOList = new ArrayList<IssuedBookDetailsDTO>();
		List<BookIssueDetails> bookIssueDetailsList = (List<BookIssueDetails>) bookIssueRepository.findAll();
		
		LOGGER.info("bookIssueDetailsList: " + bookIssueDetailsList);
		bookIssueDetailsList.stream().forEach(bookIssueDetailsOptional -> {
			//get the book author and title
			String bookAuthor = "Not-Available";
			String bookTitle = "Not-Available";
			List<Book> books = bookServiceStub.getBookById(bookIssueDetailsOptional.getBookId());
			if(books !=null && books.size()>0) {
				bookAuthor=books.get(0).getAuthor();
				bookTitle=books.get(0).getTitle();
			}
			
			issueBookDTOList.add(new IssuedBookDetailsDTO(bookIssueDetailsOptional.getId(),
					bookIssueDetailsOptional.getBookId(), bookIssueDetailsOptional.getIssuedTo(),
					bookIssueDetailsOptional.getIssuedOn(), bookIssueDetailsOptional.getIssuedForDays(),
					bookIssueDetailsOptional.getReturnedFlag(), bookIssueDetailsOptional.getReturnedOn(),
					bookAuthor, bookTitle));
		});
		return issueBookDTOList;
	}



	@Override
	public List<IssuedBookDetailsDTO> searchIssuedBookWithStudentId(String studentId) {

		LOGGER.info("searchIssueBookId in Service for studentId: " + studentId);
		List<IssuedBookDetailsDTO> issueBookDTOList = new ArrayList<IssuedBookDetailsDTO>();
		List<BookIssueDetails> bookIssueDetailsList = bookIssueRepository.findByStudentId(studentId);
		
		LOGGER.info("bookIssueDetailsList: " + bookIssueDetailsList);
		bookIssueDetailsList.stream().forEach(bookIssueDetailsOptional -> {
			//get the book author and title
			String bookAuthor = "Not-Available";
			String bookTitle = "Not-Available";
			List<Book> books = bookServiceStub.getBookById(bookIssueDetailsOptional.getBookId());
			if(books !=null && books.size()>0) {
				bookAuthor=books.get(0).getAuthor();
				bookTitle=books.get(0).getTitle();
			}
			
			issueBookDTOList.add(new IssuedBookDetailsDTO(bookIssueDetailsOptional.getId(),
					bookIssueDetailsOptional.getBookId(), bookIssueDetailsOptional.getIssuedTo(),
					bookIssueDetailsOptional.getIssuedOn(), bookIssueDetailsOptional.getIssuedForDays(),
					bookIssueDetailsOptional.getReturnedFlag(), bookIssueDetailsOptional.getReturnedOn(),
					bookAuthor, bookTitle));
		});
		return issueBookDTOList;
	}

	@Override
	public String returnBook(ReturnBookDTO returnBook) {
		Boolean transactionSuccess = false;
		LOGGER.info("Returning book: " + returnBook);
		
		  try { 
			  List<BookInventory> bookInventory =
					  inventoryServiceStub.getInventoryForABook(returnBook.getBookId());
			  LOGGER.info("bookInventory: " + bookInventory);
		  
			  if(bookInventory!=null && bookInventory.get(0).getCount()>0) { try {
				  //Decrease the inventory by 1 
				  BookInventory bookInventoryResp = inventoryServiceStub.saveInventoryForABook( new
						  BookInventory(returnBook.getBookId(), bookInventory.get(0).getCount()+1 ));
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
				 notifyUser(returnBook.getIssuedToEmailId(),"Book returned: BookID: '" +
						 returnBook.getBookId() + "' on '" + new Date() +
						  "' by '" + returnBook.getIssuedToEmailId() + "'"); 
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

	private void notifyUser(String notificationTo, String message) {
		LOGGER.info("notifying user...");
		try {
			MQUtility.sendMessageAsync(notificationTo + "#" + message);
		} catch (Exception e) {
			LOGGER.error("Error occured while sending message to Queue" + e);
		}
	}

}
