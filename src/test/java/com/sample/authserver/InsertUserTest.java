package com.sample.authserver;

import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sample.authserver.entity.User;
import com.sample.authserver.repo.UserJpaRepo;


@SpringBootTest
class InsertUserTest {
	
	@Autowired
	private UserJpaRepo userJpaRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void inserUser() {
		userJpaRepo.save(User.builder()
				.uid("lhwda1388@gmail.com")
				.password(passwordEncoder.encode("1234"))
				.name("이현우")
				.roles(Collections.singletonList("ROLE_USER"))
				.build());
	}
}
