package com.letapp.photoapp.api.configServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class PhotoApiConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoApiConfigServerApplication.class, args);
	}

}
