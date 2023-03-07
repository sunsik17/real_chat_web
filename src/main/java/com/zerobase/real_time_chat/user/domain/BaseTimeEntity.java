package com.zerobase.real_time_chat.user.domain;

import java.time.LocalDateTime;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


@EntityListeners(EntityListeners.class)
@MappedSuperclass
public class BaseTimeEntity {

	@CreatedDate
	private LocalDateTime createDateTime;

	@LastModifiedDate
	private LocalDateTime updateDateTime;
}
