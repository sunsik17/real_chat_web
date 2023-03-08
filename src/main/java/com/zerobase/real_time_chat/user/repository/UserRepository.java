package com.zerobase.real_time_chat.user.repository;

import com.zerobase.real_time_chat.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUserEmail(String email);
}
