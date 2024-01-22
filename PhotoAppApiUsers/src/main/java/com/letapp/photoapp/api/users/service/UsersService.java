package com.letapp.photoapp.api.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.letapp.photoapp.api.users.model.vo.UserDto;

public interface UsersService extends UserDetailsService{
	
	public UserDto createUser(UserDto userDto);
	public UserDto getUserDetailsByEmail(String email);

}
