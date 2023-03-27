package com.zerobase.real_time_chat.chat.config;

import com.zerobase.real_time_chat.exception.RealChatWebException;
import com.zerobase.real_time_chat.security.JwtUtil;
import com.zerobase.real_time_chat.type.ErrorCode;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatPreHandler implements ChannelInterceptor {

	private final JwtUtil jwtUtil;
	private static final String AUTH_PREFIX = "Bearer ";

	private final Map<String, String> session = new ConcurrentHashMap<>();

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {

		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

		if (Objects.requireNonNull(accessor.getCommand()).equals(StompCommand.CONNECT)) {

			String authorization =
				String.valueOf(accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION));
			log.info("WebSocket Connect ===== authorization : " + authorization);
			accessor.addNativeHeader(HttpHeaders.AUTHORIZATION, authorization);

			String token = authorization.substring(AUTH_PREFIX.length());
			log.info("WebSocket Connect ===== token : " + token);

			accessor.addNativeHeader("Authorization", token);

			if (!jwtUtil.validateToken(token)) {
				log.error("token 검증 실패 : 시간 만료 or 잘못된 토큰");
				throw new RealChatWebException(ErrorCode.INVALID_TOKEN);
			}
//
//			final Authentication authentication = jwtUtil.getAuthentication(token);
//
//			SecurityContextHolder
//				.getContext()
//				.setAuthentication(authentication);
		}

		return message;
	}
	@EventListener(SessionConnectEvent.class)
	public void onConnection (SessionConnectEvent event) {
		String token = Objects.requireNonNull(
			event.getMessage().getHeaders().get("nativeHeaders")).toString().split(",")[5];
		String simpSessionId = Objects.requireNonNull(
			event.getMessage().getHeaders().get("simpSessionId")).toString();
		session.put(simpSessionId, token);
		log.info("================{}{}====================",session.keySet(), session.values());
	}

	@EventListener(SessionDisconnectEvent.class)
	public void disconnection (SessionDisconnectEvent event) {
		session.remove(event.getSessionId());
		log.info("================{}{}====================",session.keySet(), session.values());
	}
}
