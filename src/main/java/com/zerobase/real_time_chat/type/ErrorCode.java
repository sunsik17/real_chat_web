package com.zerobase.real_time_chat.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	INTERNAL_SERVER_ERROR("내부 서버 오류가 발생했습니다."),
	THIS_EMAIL_ALREADY_EXISTS("해당 이메일은 이미 존재합니다."),
	THE_PASSWORD_IS_WRONG("패스워드가 틀렸습니다"),
	PHONE_NUMBER_FORMAT_ERROR("잘못된 핸드폰번호 형식입니다.");



	private final String description;
}
