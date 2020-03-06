package com.sample.authserver.controller;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.sample.authserver.model.OAuthToken;

@RestController
@RequestMapping("/oauth2")
public class Oauth2Controller {
 
	@Autowired
    private Gson gson;
	@Autowired
    private RestTemplate restTemplate;
 
    @GetMapping(value = "/callback")
    public OAuthToken calback(@RequestParam String code) {
    	
    	System.out.println("=========== callback");
    	
        String credentials = "testClientId:testSecret";
        String encodedCredentials = new String(Base64.getEncoder().encode(credentials.getBytes()));
 
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Basic " + encodedCredentials);
 
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", "http://localhost:8081/oauth2/callback");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8081/oauth/token", request, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return gson.fromJson(response.getBody(), OAuthToken.class);
        }
        return null;
    }
    
    @GetMapping(value = "/refresh")
    public OAuthToken refresh(@RequestParam String refreshToken) {
    	
    	System.out.println("=========== callback");
    	
        String credentials = "testClientId:testSecret";
        String encodedCredentials = new String(Base64.getEncoder().encode(credentials.getBytes()));
 
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Basic " + encodedCredentials);
 
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("refresh_token", refreshToken);
        params.add("grant_type", "refresh_token");
        
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8081/oauth/token", request, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return gson.fromJson(response.getBody(), OAuthToken.class);
        }
        return null;
    }
    
    
}
