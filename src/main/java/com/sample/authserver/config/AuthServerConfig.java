package com.sample.authserver.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.sample.authserver.service.CustomUserDetailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private DataSource dataSource;
	@Autowired
	private CustomUserDetailService userDetailService;
	
	@Value("${security.oauth2.jwt.signKey}")
	private String signKey;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// inmemory
		/*
		clients.inMemory()
			.withClient("testClientId")
			.secret(passwordEncoder.encode("testSecret"))
			.redirectUris("http://localhost:8081/oauth2/callback")
			.authorizedGrantTypes("authorization_code")
			.scopes("read", "write")
			.accessTokenValiditySeconds(60 * 60 * 5);
		*/
		clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
			
	}
	
	/*
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// jdbc 토큰저장
		endpoints.tokenStore(new JdbcTokenStore(dataSource)).userDetailsService(userDetailService);
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()")
			.checkTokenAccess("isAuthenticated()")
			.allowFormAuthenticationForClients();
	}
	*/
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// jwt
		endpoints.accessTokenConverter(accessTokenConverter()).userDetailsService(userDetailService);
	}
	
	/*
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		log.debug("signKey {}", signKey);
		JwtAccessTokenConverter convter = new JwtAccessTokenConverter();
		convter.setSigningKey(signKey);
		return convter;
	}
	*/
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("sampleauth.jks"), "storepwd".toCharArray());
		JwtAccessTokenConverter convter = new JwtAccessTokenConverter();
		convter.setKeyPair(keyStoreKeyFactory.getKeyPair("samplejks"));
		
		return convter;
	}
}
