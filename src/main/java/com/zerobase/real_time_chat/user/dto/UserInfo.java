package com.zerobase.real_time_chat.user.dto;

import com.zerobase.real_time_chat.user.domain.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserInfo {

	private String name;
	private String email;
	private String phoneNumber;
	private LocalDateTime createDateTime;

	public static UserInfo fromEntity(User user) {
		return UserInfo
			.builder()
			.email(user.getUserEmail())
			.name(user.getUsername())
			.phoneNumber(user.getPhoneNumber())
			.createDateTime(user.getCreateDateTime())
			.build();
	}
}
