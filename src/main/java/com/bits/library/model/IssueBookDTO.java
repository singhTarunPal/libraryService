package com.bits.library.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueBookDTO {
	
	Integer issueId;
	String bookISBN;
	String issuedTo;
	
	Date issuedOn;
	Integer issuedForDays;
	
	
	public IssueBookDTO(Integer issueId, String bookISBN, String issuedTo, Date issuedOn, Integer issuedForDays) {
		super();
		this.issueId  = issueId;
		this.bookISBN = bookISBN;
		this.issuedTo = issuedTo;
		this.issuedOn = issuedOn;
		this.issuedForDays = issuedForDays;
	}


	@Override
	public String toString() {
		return "IssueBookDTO [issueId=" + issueId + ", bookISBN=" + bookISBN + ", issuedTo=" + issuedTo + ", issuedOn="
				+ issuedOn + ", issuedForDays=" + issuedForDays + "]";
	}
	
	
}
