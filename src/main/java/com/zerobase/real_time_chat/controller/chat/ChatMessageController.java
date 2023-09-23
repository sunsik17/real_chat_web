package com.zerobase.real_time_chat.controller.chat;

import com.zerobase.real_time_chat.domain.chat.dto.ChatMessage;
import com.zerobase.real_time_chat.domain.chat.service.ChatMessageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
//@PostAuthorize("hasAnyAuthority('USER')")
public class ChatMessageController {
	private final ChatMessageService chatMessageService;

	@MessageMapping("/send")
	public void sendMessage(ChatMessage chatMessage) {
		chatMessageService.sendMessage(chatMessage);
	}

	@GetMapping("/message/{id}")
	@PreAuthorize("hasAnyAuthority('USER')")
	public List<ChatMessage> loadPreviousMessages(@PathVariable Long id) {

		return chatMessageService.loadPreviousMessages(id);
	}
}


