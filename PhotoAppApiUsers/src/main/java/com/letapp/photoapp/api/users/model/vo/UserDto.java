package com.letapp.photoapp.api.users.model.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1974257690801143789L;
	public String userId;
	public String email;
	public String password;
	public String encryptedPassword;
	public String firstName;
	public String lastName;
}
