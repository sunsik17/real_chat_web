package com.zerobase.real_time_chat.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	INVALID_REQUEST("잘못된 요청입니다."),
	INVALID_AUTHORIZATION("잘못 된 AUTHORIZATION"),
	INVALID_ACCOUNT("잘못된 계정입니다."),
	INVALID_TOKEN("잘못 된 TOKEN"),
	EXPIRED_TOKEN("만료 된 TOKEN"),
	INTERNAL_SERVER_ERROR("내부 서버 오류가 발생했습니다."),
	THIS_EMAIL_ALREADY_EXISTS("해당 이메일은 이미 존재합니다."),
	NO_CHATROOM_CREATED("없는 채팅방 입니다."),
	PHONE_NUMBER_FORMAT_ERROR("잘못된 핸드폰번호 형식입니다.");



	private final String description;
}
