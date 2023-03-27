package com.zerobase.real_time_chat.chat.domain;


import com.zerobase.real_time_chat.domain.BaseTimeEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageEntity extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String senderEmail;
	@ManyToOne
	@JoinColumn(name = "chatRoomId", nullable = false)
	private ChatRoomEntity chatRoomEntity;
	private String message;
}
