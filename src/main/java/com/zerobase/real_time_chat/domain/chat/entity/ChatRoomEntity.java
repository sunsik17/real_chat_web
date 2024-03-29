package com.zerobase.real_time_chat.domain.chat.entity;

import com.zerobase.real_time_chat.domain.BaseTimeEntity;
import com.zerobase.real_time_chat.domain.user.entity.User;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomEntity extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	@OneToMany(fetch = FetchType.LAZY)
	@Column
	private List<User> users;
	@OneToMany(mappedBy = "chatRoomEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ChatMessageEntity> messages;

	@Setter
	private boolean activation;
	public void addUser(User user) {
		this.users.add(user);
	}
}
