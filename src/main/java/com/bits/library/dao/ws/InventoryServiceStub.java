package com.bits.library.dao.ws;

import java.util.List;

import com.bits.library.model.BookInventory;

public interface InventoryServiceStub {
	List<BookInventory> getInventoryForABook(String bookId);
	BookInventory saveInventoryForABook(BookInventory bookInventory);
}
