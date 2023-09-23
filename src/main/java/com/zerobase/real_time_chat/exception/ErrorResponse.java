package com.zerobase.real_time_chat.exception;

import com.zerobase.real_time_chat.type.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
	private ErrorCode errorCode;
	private String errorMessage;
}
