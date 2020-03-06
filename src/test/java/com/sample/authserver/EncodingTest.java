package com.sample.authserver;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class EncodingTest {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Test
	void test() {
		// fail("Not yet implemented");
	}
	
	@Test
	public void encodeTest() {
		System.out.println(passwordEncoder.encode("testSecret"));
	}

}
