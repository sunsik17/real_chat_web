package com.zerobase.real_time_chat.exception;

import com.zerobase.real_time_chat.type.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RealChatWebException extends RuntimeException{
	private ErrorCode errorCode;
	private String errorMessage;

	public RealChatWebException(ErrorCode errorCode){
		this.errorCode = errorCode;
		this.errorMessage = errorCode.getDescription();
	}
}
