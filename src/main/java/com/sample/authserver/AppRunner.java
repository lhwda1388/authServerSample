package com.sample.authserver;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sample.authserver.entity.OAuthClientDetails;
import com.sample.authserver.entity.User;
import com.sample.authserver.repo.OAuthClientDetailRepo;
import com.sample.authserver.repo.UserJpaRepo;

@Component
public class AppRunner implements ApplicationRunner {
	
	@Autowired
	private UserJpaRepo userJpaRepo;
	
	@Autowired
	private OAuthClientDetailRepo oAuthClientDetailRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		userJpaRepo.save(
				User.builder()
				.uid("lhwda1388@gmail.com")
				.password(passwordEncoder.encode("1234"))
				.name("이현우")
				.roles(Collections.singletonList("ROLE_USER"))
				.build());
		
		oAuthClientDetailRepo.save(
				OAuthClientDetails.builder()
				.clientId("testClientId")
				.clientSecret(passwordEncoder.encode("testSecret"))
				.scope("read,write")
				.authorizedGrantTypes("authorization_code,refresh_token")
				.webServerRedirectUri("http://localhost:8081/oauth2/callback")
				.authorities("ROLE_USER")
				.accessTokenValidity(36000)
				.refreshTokenValidity(50000)
				.build());
		
	}
}
