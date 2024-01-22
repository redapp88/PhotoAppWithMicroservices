package com.letapp.photoapp.api.users.secrity;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import javax.crypto.spec.*;
import javax.crypto.spec.SecretKeySpec;


import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letapp.photoapp.api.users.model.LoginRequestModel;
import com.letapp.photoapp.api.users.model.vo.UserDto;
import com.letapp.photoapp.api.users.service.UsersService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private UsersService usersService;
	private Environment environment;

	public AuthenticationFilter(AuthenticationManager authenticationManager, UsersService usersService,
			Environment environment) {
		super(authenticationManager);
		this.usersService = usersService;
		this.environment = environment;

	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {

			LoginRequestModel creds = new ObjectMapper().readValue(req.getInputStream(), LoginRequestModel.class);

			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
		Authentication auth) throws IOException, ServletException {
		String username = ((User) auth.getPrincipal()).getUsername();
		UserDto userDto = this.usersService.getUserDetailsByEmail(username);
		
		String secretToken = this.environment.getProperty("token.secret");
		byte[] tokenBytes = Base64.encode(secretToken.getBytes());
		SecretKeySpec secretKey = new SecretKeySpec(tokenBytes, SignatureAlgorithm.HS512.getJcaName());
		
		
		String token=Jwts.builder()
		.setSubject(userDto.getUserId())
		.setExpiration(Date.from(Instant.now().plusMillis(Long.parseLong(this.environment.getProperty("token.expiration_time")))))
		.setIssuedAt(new Date())
		.signWith(secretKey,SignatureAlgorithm.HS512)
		.compact();
		
		res.addHeader("token", token);
		res.addHeader("userId", userDto.getUserId());
		
	}

}
