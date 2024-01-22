package com.letapp.photoapp.api.users.secrity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.letapp.photoapp.api.users.service.UsersService;


@Configuration
@EnableWebSecurity
public class WebSecurity {
	private Environment environment;
	private UsersService usersService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	
	@Autowired
	public WebSecurity(Environment environment,UsersService usersService,BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.environment = environment;
		this.usersService =usersService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		// configure AuthenticationManagerBuilder (class gere l'authentication et cherche si l'utilisateur existe)
		AuthenticationManagerBuilder authenticationManagerBuilder= http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(usersService).passwordEncoder(bCryptPasswordEncoder);
		
		AuthenticationManager authenticationManager= authenticationManagerBuilder.build();
		
		AuthenticationFilter authenticationFilter=new AuthenticationFilter(authenticationManager,usersService,environment);
		http.csrf().disable();
		http.authorizeHttpRequests()
		.requestMatchers(HttpMethod.POST,"/login").permitAll()
		.requestMatchers(HttpMethod.POST,"/users").access(new WebExpressionAuthorizationManager("hasIpAddress('"+this.environment.getProperty("gateway.ip")+"')"))
		.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
		
		.and()
		.addFilter(authenticationFilter)
		.authenticationManager(authenticationManager)
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.headers().frameOptions().disable();
		
		return http.build();
		
		
		
	}

}
