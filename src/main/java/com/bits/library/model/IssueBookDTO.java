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

	@Override
	public String toString() {
		return "IssueBookDTO [issueId=" + issueId + ", bookISBN=" + bookISBN + ", issuedTo=" + issuedTo + ", issuedOn="
				+ issuedOn + ", issuedForDays=" + issuedForDays + "]";
	}
	
	
}
