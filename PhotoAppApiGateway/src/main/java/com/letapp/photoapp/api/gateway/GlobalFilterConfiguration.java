package com.letapp.photoapp.api.gateway;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
@Slf4j
@Configuration
public class GlobalFilterConfiguration {
	@Order(0)
	@Bean
	public GlobalFilter mySecondeFilter() {

		return (exchange,chaine)->{
			log.info("my seconde pre-filter is executed");
			return chaine.filter(exchange).then(Mono.fromRunnable(()->{
			log.info("my seconde post-filter is executed");
			}));
			
			
		};
	}
	@Order(1)
	@Bean
	public GlobalFilter myThirdFilter() {
		return (exchange,chaine)->{
			log.info("my third pre-filter is executed");
			return chaine.filter(exchange).then(Mono.fromRunnable(()->{
			log.info("my third post-filter is executed");
			}));
			
			
		};
	}

}
