package com.zerobase.real_time_chat.user.controller;

import com.zerobase.real_time_chat.user.dto.RegisterUser;
import com.zerobase.real_time_chat.user.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/user/register")
	public RegisterUser.Response register(
		@RequestBody @Valid RegisterUser.Request request
	) {
		return RegisterUser.Response.from(
			userService.registerUser(request)
		);
	}
}

