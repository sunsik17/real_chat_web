package com.zerobase.real_time_chat.chat.config;

import com.zerobase.real_time_chat.security.JwtUtil;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatPreHandler implements ChannelInterceptor {

	private final JwtUtil jwtUtil;
	private static final String AUTH_PREFIX = "Bearer ";

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {

		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

		String authorization =
			String.valueOf(accessor.getFirstNativeHeader("Authorization"));
		log.info("WebSocket Connect ===== authorization : " + authorization);

		if (Objects.requireNonNull(accessor.getCommand()).equals(StompCommand.CONNECT)) {

			String token = authorization.substring(AUTH_PREFIX.length());
			log.info("WebSocket Connect ===== token : " + token);

			final Authentication authentication = jwtUtil.getAuthentication(token);

		}
		return message;
	}
}
