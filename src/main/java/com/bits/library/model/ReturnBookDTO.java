package com.bits.library.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnBookDTO {
	
	//cannot be null
	private Integer issueId;
	private String bookId;
	private String issuedToEmailId;	
	private Date issuedOn;
	
	
	@Override
	public String toString() {
		return "IssueBookDTO [issueId=" + issueId + ", bookISBN=" + bookId + ", issuedTo=" + issuedToEmailId + ", issuedOn="
				+ issuedOn  +" ]";
	}
	
}
