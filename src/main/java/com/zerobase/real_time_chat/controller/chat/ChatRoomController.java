package com.zerobase.real_time_chat.controller.chat;

import com.zerobase.real_time_chat.domain.chat.dto.ChatRoom;
import com.zerobase.real_time_chat.domain.chat.dto.CreateChatRoomInfo;
import com.zerobase.real_time_chat.domain.chat.service.ChatRoomService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatRoom")
@PreAuthorize("hasAnyAuthority('USER')")
public class ChatRoomController {

	private final ChatRoomService chatRoomService;

	@GetMapping
	public List<ChatRoom> getAllChatRoom(
		Authentication authentication) {

		return chatRoomService.getAllChatRoom(authentication.getName());
	}

	@PostMapping("/create")
	public ChatRoom createChatRoom(
		Authentication authentication,
		@RequestBody CreateChatRoomInfo createChatRoominfo) {

		return chatRoomService.createRoom(authentication.getName(), createChatRoominfo);
	}

	@PostMapping("/invite")
	public ResponseEntity<?> inviteUser(
		@RequestBody ChatRoom.Invite invite) {
		return ResponseEntity.ok().body(chatRoomService.inviteUser(invite));
	}
}
