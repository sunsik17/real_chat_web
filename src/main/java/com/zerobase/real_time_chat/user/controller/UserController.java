package com.zerobase.real_time_chat.user.controller;

import com.zerobase.real_time_chat.user.dto.LoginUser;
import com.zerobase.real_time_chat.user.dto.RegisterUser;
import com.zerobase.real_time_chat.user.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/register")
	public RegisterUser.Response register(
		@RequestBody @Valid RegisterUser.Request request
	) {
		return RegisterUser.Response.from(
			userService.registerUser(request)
		);
	}

	@PostMapping("/login")
	public LoginUser.Response login(
		@RequestBody @Valid LoginUser.Request request
	) {
		return LoginUser.Response.from(
			userService.login(request)
		);
	}
}

