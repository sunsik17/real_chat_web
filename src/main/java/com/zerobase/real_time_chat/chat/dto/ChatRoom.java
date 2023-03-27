package com.zerobase.real_time_chat.chat.dto;

import com.zerobase.real_time_chat.chat.domain.ChatRoomEntity;
import com.zerobase.real_time_chat.user.domain.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatRoom {

	private Long ChatRoomId;
	private List<String> members;
	private List<ChatMessage> messages;

	public static ChatRoom fromEntity(ChatRoomEntity entity) {
		return ChatRoom
			.builder()
			.ChatRoomId(entity.getId())
			.members(entity
				.getUsers()
				.stream()
				.map(User::getUserEmail)
				.collect(Collectors.toList()))
			.messages(entity.getMessages()
				.stream()
				.map(ChatMessage::fromEntity)
				.collect(Collectors.toList()))
			.build();
	}
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class Invite {
		private String email;
		private Long chatRoomId;
	}
}
