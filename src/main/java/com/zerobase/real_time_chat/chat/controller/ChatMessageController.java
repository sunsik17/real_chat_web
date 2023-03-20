package com.zerobase.real_time_chat.chat.controller;

import com.zerobase.real_time_chat.chat.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatMessageController {

	private final SimpMessageSendingOperations simpMessageSendingOperations;

	@MessageMapping("/send")
	public void sendMessage(ChatMessage chatMessage) {

		simpMessageSendingOperations.convertAndSend("/sub/chatRoom/" + chatMessage.getChatRoomId(), chatMessage);
	}
}
