package com.letapp.photoapp.api.users.web;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.letapp.photoapp.api.users.model.CreateUserRequestModel;
import com.letapp.photoapp.api.users.model.CreateUserResponseModel;
import com.letapp.photoapp.api.users.model.vo.UserDto;
import com.letapp.photoapp.api.users.service.UsersService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UsersController {
	@Autowired
	private UsersService usersService;
	@Autowired
	private Environment environment;
	
@GetMapping("/status/check")
	public String status() {
		return "working on port : "+this.environment.getProperty("local.server.port")+" with token sercret: "+ this.environment.getProperty("token.secret") ;
	}
@PostMapping
public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userRequest) {
	ModelMapper mapper = new ModelMapper();
	mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	UserDto userDto = mapper.map(userRequest, UserDto.class);
	UserDto createdUser = this.usersService.createUser(userDto);
	 CreateUserResponseModel createUserResponse = mapper.map(createdUser, CreateUserResponseModel.class);
	
	return  ResponseEntity.status(HttpStatus.CREATED).body(createUserResponse);
}
}
