package com.bits.library.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueBookDTO {
	
	private Integer issueId;
	private String bookId;
	private String issuedToEmailId;	
	private Date issuedOn;	
	private Integer issuedForDays;
	private String returnedFlag;
	private Date returnedOn;
	
	@Override
	public String toString() {
		return "IssueBookDTO [issueId=" + issueId + ", bookISBN=" + bookId + ", issuedTo=" + issuedToEmailId + ", issuedOn="
				+ issuedOn + ", issuedForDays=" + issuedForDays + ", returnedFlag=" + returnedFlag + ", returnedOn="
				+ returnedOn + "]";
	}
	
}
