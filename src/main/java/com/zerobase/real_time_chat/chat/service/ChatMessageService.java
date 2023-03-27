package com.zerobase.real_time_chat.chat.service;

import com.zerobase.real_time_chat.chat.domain.ChatMessageEntity;
import com.zerobase.real_time_chat.chat.domain.ChatRoomEntity;
import com.zerobase.real_time_chat.chat.dto.ChatMessage;
import com.zerobase.real_time_chat.chat.repository.ChatMessageRepository;
import com.zerobase.real_time_chat.chat.repository.ChatRoomRepository;
import com.zerobase.real_time_chat.exception.RealChatWebException;
import com.zerobase.real_time_chat.type.ErrorCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatMessageService {
	private final ChatMessageRepository chatMessageRepository;
	private final ChatRoomRepository chatRoomRepository;
	private final SimpMessageSendingOperations simpMessageSendingOperations;

	public void sendMessage(ChatMessage chatMessage) {

		ChatRoomEntity chatRoom = chatRoomRepository.findById(chatMessage.getChatRoomId())
				.orElseThrow(() ->
					new RealChatWebException(ErrorCode.NO_CHATROOM_CREATED));

		chatMessageRepository.save(ChatMessageEntity
			.builder()
				.message(chatMessage.getMessage())
				.chatRoomEntity(chatRoom)
				.senderEmail(chatMessage.getSender())
			.build());
		simpMessageSendingOperations.convertAndSend("/sub/chatRoom/" +
			chatMessage.getChatRoomId(), chatMessage);
	}

	public List<ChatMessage> loadPreviousMessages(Long id) {

		ChatRoomEntity chatRoom = chatRoomRepository.findById(id)
			.orElseThrow(() -> new RealChatWebException(ErrorCode.INVALID_REQUEST));

		return chatRoom.getMessages()
			.stream()
			.map(ChatMessage::fromEntity)
			.collect(Collectors.toList());
	}
}
