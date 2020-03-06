package com.sample.authserver.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.authserver.entity.User;

public interface UserJpaRepo extends JpaRepository<User, Long> {
	Optional<User> findByUid(String email);
}
