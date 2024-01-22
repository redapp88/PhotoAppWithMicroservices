package com.letapp.photoapp.api.users.entities;

import java.io.Serializable;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1117192331227459182L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false,unique = true)
	public String userId;
	@Column(nullable = false,length = 50 ,unique = true)
	public String email;
	@Column(nullable = false)
	public String encryptedPassword;
	@Column(nullable = false,length = 50)
	public String firstName;
	@Column(nullable = false,length = 50)
	public String lastName;

	
	
	
	

}
