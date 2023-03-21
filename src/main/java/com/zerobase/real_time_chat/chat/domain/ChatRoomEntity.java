package com.zerobase.real_time_chat.chat.domain;

import com.zerobase.real_time_chat.user.domain.User;
import java.util.List;
import javax.persistence.Entity;
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
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@OneToMany
	private List<User> users;
	@OneToMany(mappedBy = "chatRoomEntity")
	private List<ChatMessageEntity> messages;

	public void addUser(User user) {
		this.users.add(user);
	}
}