package com.zerobase.real_time_chat.domain.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class LoginUser {
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
	}
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Response {
		private String token;

		public static Response from(String token) {
			return Response.builder().token(token).build();
		}
	}
}
