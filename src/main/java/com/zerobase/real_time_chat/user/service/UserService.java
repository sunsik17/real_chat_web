package com.zerobase.real_time_chat.user.service;

import com.zerobase.real_time_chat.type.ErrorCode;
import com.zerobase.real_time_chat.user.domain.User;
import com.zerobase.real_time_chat.user.dto.RegisterUser;
import com.zerobase.real_time_chat.user.dto.UserInfo;
import com.zerobase.real_time_chat.user.exception.RegistException;
import com.zerobase.real_time_chat.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public UserInfo registerUser(RegisterUser.Request request) {

		validateRegisterUser(request.getEmail());

		return UserInfo.fromEntity(
			userRepository.save(User.builder()
				.userName(request.getName())
				.userEmail(request.getEmail())
				.phoneNumber(request.getPhoneNumber())
				.password(request.getPassword())
				.build())
		);
	}
	private void validateRegisterUser(String email) {
		if (userRepository.findByUserEmail(email).isPresent()){
			throw new RegistException(ErrorCode.THIS_EMAIL_ALREADY_EXISTS);
		}
	}

}
