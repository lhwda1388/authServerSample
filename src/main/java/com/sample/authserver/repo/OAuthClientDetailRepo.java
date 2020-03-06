package com.sample.authserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.authserver.entity.OAuthClientDetails;

public interface OAuthClientDetailRepo extends JpaRepository<OAuthClientDetails, Long>{
	
}
