package com.zerobase.real_time_chat.chat.dto;

import com.zerobase.real_time_chat.chat.domain.ChatMessageEntity;
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
	private String message;

	public static ChatMessage fromEntity(ChatMessageEntity entity) {
		return ChatMessage.builder()
			.sender(entity.getSenderEmail())
			.chatRoomId(entity.getChatRoomEntity().getId())
			.message(entity.getMessage())
			.build();
	}
}
