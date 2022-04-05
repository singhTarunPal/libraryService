package com.bits.library.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssuedBookDetailsDTO {
	
	private Integer issueId;
	private String bookId;
	private String issuedToEmailId;	
	private Date issuedOn;	
	private Integer issuedForDays;
	private String returnedFlag;
	private Date returnedOn;
	private String bookAuthor;
	private String bookTitle;
	
	@Override
	public String toString() {
		return "IssuedBookDetailsDTO [issueId=" + issueId + ", bookId=" + bookId + ", issuedToEmailId="
				+ issuedToEmailId + ", issuedOn=" + issuedOn + ", issuedForDays=" + issuedForDays + ", returnedFlag="
				+ returnedFlag + ", returnedOn=" + returnedOn + ", bookAuthor=" + bookAuthor + ", bookTitle="
				+ bookTitle + "]";
	}	
}
