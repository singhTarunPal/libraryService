package com.bits.library.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book_issue")
@XmlRootElement(name = "bookIssue")
public class BookIssueDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;	
	
	@Column(name="book_id")
	private String bookId;
	
	@Column(name="issued_to")
	private String issuedTo;
	
	@Column(name="issued_on")
	private Date issuedOn;
	
	@Column(name="issued_for_days")
	private Integer issuedForDays;
	
	@Column(name="returned_flag")
	private String returnedFlag;
	
	@Column(name="returned_on")
	private Date returnedOn;
	
	
	
}
