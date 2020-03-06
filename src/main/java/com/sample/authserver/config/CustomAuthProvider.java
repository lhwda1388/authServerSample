package com.sample.authserver.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sample.authserver.entity.User;
import com.sample.authserver.repo.UserJpaRepo;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAuthProvider implements AuthenticationProvider {
	
	@NonNull
	private PasswordEncoder passwordEncoder;
	@NonNull
	private UserJpaRepo userJpaRepo;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = authentication.getName();
		String pass = authentication.getCredentials().toString();
		
		User user = userJpaRepo
						.findByUid(email)
						.orElseThrow(() -> new UsernameNotFoundException("user not exist"));
		if (!passwordEncoder.matches(pass, user.getPassword())) {
			throw new BadCredentialsException("password is not valid");
		}
		
		return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
