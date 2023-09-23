package com.zerobase.real_time_chat.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserForChatRoom {

	private String name;
	private String userEmail;
	private String phoneNumber;

}
