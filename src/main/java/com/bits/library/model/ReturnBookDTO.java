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
	private String bookISBN;
	private String issuedTo;	
	private Date issuedOn;	
	private Integer issuedForDays;
	private String returnedFlag;
	private Date returnedOn;
	
	@Override
	public String toString() {
		return "IssueBookDTO [issueId=" + issueId + ", bookISBN=" + bookISBN + ", issuedTo=" + issuedTo + ", issuedOn="
				+ issuedOn + ", issuedForDays=" + issuedForDays + ", returnedFlag=" + returnedFlag + ", returnedOn="
				+ returnedOn + "]";
	}
	
}
