package com.zerobase.real_time_chat.domain.user.service;

import static com.zerobase.real_time_chat.type.ErrorCode.INVALID_ACCOUNT;
import static com.zerobase.real_time_chat.type.ErrorCode.INVALID_TOKEN;
import static com.zerobase.real_time_chat.type.ErrorCode.THIS_EMAIL_ALREADY_EXISTS;

import com.zerobase.real_time_chat.domain.chat.entity.ChatMessageEntity;
import com.zerobase.real_time_chat.domain.chat.repository.ChatMessageRepository;
import com.zerobase.real_time_chat.domain.user.dto.LoginUser;
import com.zerobase.real_time_chat.domain.user.dto.RegisterUser.Request;
import com.zerobase.real_time_chat.domain.user.dto.UserInfo;
import com.zerobase.real_time_chat.domain.user.entity.User;
import com.zerobase.real_time_chat.domain.user.repository.UserRepository;
import com.zerobase.real_time_chat.exception.RealChatWebException;
import com.zerobase.real_time_chat.security.JwtUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
	private static final String DELETE_USER_NAME = "이름 없음";
	private final UserRepository userRepository;
	private final ChatMessageRepository chatMessageRepository;
	private final BCryptPasswordEncoder encoder;
	private final JwtUtil jwtUtil;

	public UserInfo registerUser(Request request) {

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

		validateLoginUser(user, request.getPassword());

		return jwtUtil.createToken(user.getUserEmail());
	}

	public UserInfo details(String email) {
		log.info(email);
		User user =
			userRepository.findByUserEmail(email)
			.orElseThrow(() -> new RealChatWebException(INVALID_ACCOUNT));
		return UserInfo.fromEntity(user);
	}

	private void validateLoginUser(User user, String password) {

		log.info("selectedPw:{} pw:{}", user.getPassword(), password);
    
		if (!encoder.matches(password, user.getPassword())) {
			throw new RealChatWebException(INVALID_ACCOUNT);
		}
	}

	public void deleteUser(Authentication authentication) {
		User user = userRepository.findByUserEmail(authentication.getName())
			.orElseThrow(() -> new RealChatWebException(INVALID_TOKEN));

		user.setUsername(DELETE_USER_NAME);
		user.setUserEmail(DELETE_USER_NAME);
		userRepository.save(user);

		List<ChatMessageEntity> messageEntities =
			chatMessageRepository.findAllBySenderEmail(authentication.getName());

		for (ChatMessageEntity e : messageEntities){
			e.setSenderEmail(DELETE_USER_NAME);
			chatMessageRepository.save(e);
		}
	}
}
