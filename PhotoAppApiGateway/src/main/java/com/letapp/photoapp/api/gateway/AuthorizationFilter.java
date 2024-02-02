package com.letapp.photoapp.api.gateway;

import java.util.Base64;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.google.common.net.HttpHeaders;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationFilter extends AbstractGatewayFilterFactory<AuthorizationFilter.Config> {

	@Autowired
	private Environment environement;

	//// constructor is veery important here not mentioned in the course
	public AuthorizationFilter() {
		super(Config.class);
	}

	public static class Config {

	}

	@Override
	public GatewayFilter apply(Config config) {

		return (exchange, chaine) -> {

			ServerHttpRequest request = exchange.getRequest();
			if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
				return onError(exchange, "No Authorization Header", HttpStatus.FORBIDDEN);
			String authorizationHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
			String jwt = authorizationHeader.replace("Bearer ", "");
			if (!isJwtValid(jwt)) {
				return onError(exchange, "Jwt is not valid", HttpStatus.FORBIDDEN);
			}

			return chaine.filter(exchange);

		};

	}

	private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(status);
		return response.setComplete();
	}

	private boolean isJwtValid(String jwt) {
		boolean returnValue = true;

		
		
		String subject = null;
		String tokenSecret = this.environement.getProperty("token.secret");
		byte[] secretKeyBytes = Base64.getEncoder().encode(tokenSecret.getBytes());
		SecretKeySpec signingKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());
		JwtParser parser = Jwts.parser().verifyWith(signingKey).build();

		try {
			Jwt<?, ?> parsedToken =  parser.parse(jwt);
			
			//caste veery important not mentionned in the course
			subject = ((Claims) parsedToken.getPayload()).getSubject();
		} catch (Exception e) {
			returnValue = false;
		}

		if (subject == null || subject.isEmpty())
			returnValue = false;

		return returnValue;
	}

}
