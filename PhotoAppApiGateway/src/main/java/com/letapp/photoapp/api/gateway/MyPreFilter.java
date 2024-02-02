
package com.letapp.photoapp.api.gateway;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class MyPreFilter implements GlobalFilter,Ordered {
//	final Logger logger = LoggerFactory.getLogger(MyPreFilter.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		log.info("My first pre filter is exercuted ...");
//		String requestPath = exchange.getRequest().getPath().toString();
//		log.info("the request path is " + requestPath);
//		HttpHeaders httpHeaders = exchange.getRequest().getHeaders();
//		Set<String> keys = httpHeaders.keySet();
//		keys.forEach(key -> {
//			log.info(key + ": " + httpHeaders.getFirst(key));
//		});

		return chain.filter(exchange);
	}

@Override
public int getOrder() {
	return 3;
}

}
