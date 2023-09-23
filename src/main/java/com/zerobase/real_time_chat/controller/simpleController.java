package com.zerobase.real_time_chat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class simpleController {

	@GetMapping
	public String ping() {
		return "pong";
	}

}
