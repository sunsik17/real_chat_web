package com.zerobase.real_time_chat.chat.repository;

import com.zerobase.real_time_chat.chat.domain.ChatMessageEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long>{
	List<ChatMessageEntity> findAllBySenderEmail(String senderEmail);
}
