package com.zerobase.real_time_chat.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.zerobase.real_time_chat.type.ErrorCode;
import com.zerobase.real_time_chat.user.domain.User;
import com.zerobase.real_time_chat.user.dto.RegisterUser;
import com.zerobase.real_time_chat.user.dto.UserInfo;
import com.zerobase.real_time_chat.exception.RealChatWebException;
import com.zerobase.real_time_chat.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	@Test
	@DisplayName("회원가입성공")
	void createAccountSuccess() {

		//given
		given(userRepository.save(any()))
			.willReturn(User.builder().userEmail("ss@ss").userName("ss").password("1234")
				.phoneNumber("0101111")
				.build());

		//when
		UserInfo userInfo = userService.registerUser(
			new RegisterUser.Request("aa@aa", "qwer", "aa", "0102222")
		);
		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

		//then
		verify(userRepository, times(1)).save(captor.capture());
		assertEquals("aa@aa", captor.getValue().getUserEmail());
		assertEquals("ss", userInfo.getName());
		assertEquals("ss@ss", userInfo.getEmail());
		assertEquals("0101111", userInfo.getPhoneNumber());
	}

	@Test
	@DisplayName("중복이메일 - 회원가입 실패")
	void registerFailed_alreadyEmail() {
		//given
		given(userRepository.findByUserEmail(anyString()))
			.willReturn(Optional.ofNullable(User.builder()
				.userEmail("ss@ss")
				.userName("ss")
				.phoneNumber("010")
				.password("1234")
				.build()));

		//when
		RealChatWebException exception = assertThrows(RealChatWebException.class,
			() -> userService.registerUser(
				new RegisterUser.Request("aa@aa", "qwer", "aa", "0102222"))
		);
		//then
		assertEquals(ErrorCode.THIS_EMAIL_ALREADY_EXISTS, exception.getErrorCode());
	}
}