package com.zerobase.real_time_chat.domain.user.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class RegisterUser {

	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	public static class Request {

		@NotBlank
		@Email
		private String email;

		@NotBlank
		private String password;

		@NotBlank
		private String name;

		@NotBlank
		private String phoneNumber;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Response {

		private String email;
		private String name;
		private String phoneNumber;
		private LocalDateTime registDateTime;

		public static Response from(UserInfo userInfo) {
			return Response.builder()
				.email(userInfo.getEmail())
				.name(userInfo.getName())
				.phoneNumber(userInfo.getPhoneNumber())
				.registDateTime(LocalDateTime.now())
				.build();
		}
	}

}
