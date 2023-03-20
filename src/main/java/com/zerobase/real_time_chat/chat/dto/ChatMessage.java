package com.zerobase.real_time_chat.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
	private String sender;
	private Long chatRoomId;
	private Object message;
}
