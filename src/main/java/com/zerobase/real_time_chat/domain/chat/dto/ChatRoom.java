package com.zerobase.real_time_chat.domain.chat.dto;

import com.zerobase.real_time_chat.domain.chat.entity.ChatRoomEntity;
import com.zerobase.real_time_chat.domain.user.entity.User;
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
	private String lastMessage;

	public static ChatRoom fromEntity(ChatRoomEntity entity) {
		int size = entity.getMessages() == null ? 0 : entity.getMessages().size();

		return ChatRoom.builder()
			.ChatRoomId(entity.getId())
			.members(entity
				.getUsers()
				.stream()
				.map(User::getUserEmail)
				.collect(Collectors.toList()))
			.lastMessage(size == 0 ? null : entity.getMessages().get(size - 1).getMessage())
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
