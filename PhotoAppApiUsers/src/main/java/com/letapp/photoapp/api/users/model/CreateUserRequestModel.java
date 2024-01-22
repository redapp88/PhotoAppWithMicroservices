package com.letapp.photoapp.api.users.model;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestModel {
	@NotNull(message = "Email cannot be null")
	@Email
	private String email;
	@NotNull(message = "Password cannot be null")
	@Size(min = 8, max = 18, message = "Password should have between 8 and 18 characters")
	private String password;
	@NotNull(message = "First name cannot be null")
	@Size(min = 2, message = "First name must beat least 2 characters")
	private String firstName;
	@NotNull(message = "Last name cannot be null")
	@Size(min = 2, message = "Last name must beat least 2 characters")
	private String lastName;
}
