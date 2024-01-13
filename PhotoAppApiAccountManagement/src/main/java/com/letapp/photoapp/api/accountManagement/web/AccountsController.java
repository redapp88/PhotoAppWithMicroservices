package com.letapp.photoapp.api.accountManagement.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/accounts")
public class AccountsController {
	@GetMapping("/status/check")
	public String status() {
		return "accounts service is working";
	}

}
