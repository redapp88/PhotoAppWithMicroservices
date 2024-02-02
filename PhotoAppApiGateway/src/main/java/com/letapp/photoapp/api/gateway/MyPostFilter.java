package com.letapp.photoapp.api.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class MyPostFilter implements GlobalFilter,Ordered {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// TODO Auto-generated method stub
		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			log.info("Post Filter executed...");
		}));
	}

	@Override
	public int getOrder() {
		return 4;
	}

}
