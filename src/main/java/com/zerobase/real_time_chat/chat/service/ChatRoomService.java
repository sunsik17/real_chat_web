package com.zerobase.real_time_chat.chat.service;

import com.zerobase.real_time_chat.chat.domain.ChatRoomEntity;
import com.zerobase.real_time_chat.chat.dto.ChatRoom;
import com.zerobase.real_time_chat.chat.dto.CreateChatRoomInfo;
import com.zerobase.real_time_chat.chat.repository.ChatRoomRepository;
import com.zerobase.real_time_chat.exception.RealChatWebException;
import com.zerobase.real_time_chat.type.ErrorCode;
import com.zerobase.real_time_chat.user.domain.User;
import com.zerobase.real_time_chat.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
	private static final String CHAT_ROOM_NAME_NULL_VALUE = "이름 없음";
	private final ChatRoomRepository chatRoomRepository;
	private final UserRepository userRepository;
	public List<ChatRoom> getAllChatRoom(String email) {

		return chatRoomRepository.findAllByUsers_UserEmail(email)
				.stream()
				.map(ChatRoom::fromEntity)
				.collect(Collectors.toList());
	}


	public ChatRoom createRoom(String email, CreateChatRoomInfo createChatRoominfo) {

		User user = userRepository.getUserByUserEmail(email);

		return ChatRoom.fromEntity(
			chatRoomRepository.save(ChatRoomEntity.builder()
				.name(createChatRoominfo.getName())
				.users(List.of(user))
				.messages(List.of())
				.build())
		);
	}

	public String inviteUser(String email, ChatRoom.Invite invite) {
		User user =
			userRepository.getUserByUserEmail(email);
		User inviteUser =
			userRepository.findByUserEmail(invite.getEmail())
				.orElseThrow(() -> new RealChatWebException(ErrorCode.INVALID_ACCOUNT));

		ChatRoomEntity chatRoom =
			chatRoomRepository.findById(invite.getChatRoomId())
				.orElseGet(() ->
					chatRoomRepository.save(ChatRoomEntity.builder()
						.name(CHAT_ROOM_NAME_NULL_VALUE)
						.users(List.of(user, inviteUser))
						.messages(List.of())
						.build())
			);

		chatRoom.addUser(inviteUser);
		chatRoomRepository.save(chatRoom);

		return invite.getEmail();
	}
}
