package com.sample.authserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sample.authserver.entity.User;
import com.sample.authserver.repo.UserJpaRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
	
	private final UserJpaRepo userJpaRepo;
	
	private final AccountStatusUserDetailsChecker detailCheck = new AccountStatusUserDetailsChecker();
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userJpaRepo.findByUid(username).orElseThrow(() -> new UsernameNotFoundException("user is not exists"));
		detailCheck.check(user);
		return user;
	}
}
