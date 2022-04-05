package com.bits.library.dao.ws;

import java.util.List;

import com.bits.library.model.Book;

public interface BookServiceStub {
	List<Book> getBookById(String bookId);	
}
