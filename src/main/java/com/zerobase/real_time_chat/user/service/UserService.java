package com.zerobase.real_time_chat.user.service;

import static com.zerobase.real_time_chat.type.ErrorCode.INVALID_ACCOUNT;
import static com.zerobase.real_time_chat.type.ErrorCode.THIS_EMAIL_ALREADY_EXISTS;

import com.zerobase.real_time_chat.security.JwtUtil;
import com.zerobase.real_time_chat.user.domain.User;
import com.zerobase.real_time_chat.user.dto.LoginUser;
import com.zerobase.real_time_chat.user.dto.RegisterUser;
import com.zerobase.real_time_chat.user.dto.UserInfo;
import com.zerobase.real_time_chat.exception.RealChatWebException;
import com.zerobase.real_time_chat.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder encoder;

	@Value("${jwt.token.secret}")
	private String key;

	public UserInfo registerUser(RegisterUser.Request request) {

		validateRegisterUser(request.getEmail());

		return UserInfo.fromEntity(
			userRepository.save(User.builder()
				.username(request.getName())
				.userEmail(request.getEmail())
				.phoneNumber(request.getPhoneNumber())
				.password(encoder.encode(request.getPassword()))
				.build())
		);
	}

	private void validateRegisterUser(String email) {
		if (userRepository.findByUserEmail(email).isPresent()) {
			throw new RealChatWebException(THIS_EMAIL_ALREADY_EXISTS);
		}
	}

	public String login(LoginUser.Request request) {
		User user =
			userRepository.findByUserEmail(request.getEmail())
				.orElseThrow(() -> new RealChatWebException(INVALID_ACCOUNT));

		validateLoginUser(user.getUserEmail(), request.getPassword());

		return JwtUtil.createToken(user.getUsername(), key);
	}

	private void validateLoginUser(String email, String password) {

		log.info("selectedPw:{} pw:{}", userRepository.findByUserEmail(email).get().getPassword(), password);

		if (!encoder.matches(password, userRepository.findByUserEmail(email).get().getPassword())) {
			throw new RealChatWebException(INVALID_ACCOUNT);
		}
	}
}
