package com.letapp.photoapp.api.users.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponseModel {

	private String userId;
	private String email;	
	private String firstName;
	private String lastName;
}
