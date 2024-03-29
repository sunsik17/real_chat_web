package com.zerobase.real_time_chat.domain.chat.repository;

import com.zerobase.real_time_chat.domain.chat.entity.ChatRoomEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {
	List<ChatRoomEntity> findAllByUsers_UserEmail(String users_userEmail);
}
