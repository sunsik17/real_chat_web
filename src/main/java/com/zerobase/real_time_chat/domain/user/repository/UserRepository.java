package com.zerobase.real_time_chat.domain.user.repository;

import com.zerobase.real_time_chat.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUserEmail(String email);
	User getUserByUserEmail(String email);
}
