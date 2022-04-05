package com.bits.library.model;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibraryUser {

        @Id
        private String _id;
        private String first_name;
        private String last_name;
        private String email;
        private String password;
        private String role;
        
		@Override
		public String toString() {
			return "LibraryUser [_id=" + _id + ", first_name=" + first_name + ", last_name=" + last_name + ", email="
					+ email + ", password=" + password + ", role=" + role + "]";
		} 
        
        
       
}