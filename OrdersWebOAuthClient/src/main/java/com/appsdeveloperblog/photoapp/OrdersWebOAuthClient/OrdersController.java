/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appsdeveloperblog.photoapp.OrdersWebOAuthClient;

import java.util.List;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrdersController {
	
	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/token")
	public String getToken(@RegisteredOAuth2AuthorizedClient("users-client-oidc") OAuth2AuthorizedClient authorizedClient){
		String token = authorizedClient.getAccessToken().getTokenValue();
		return token;
	}

	@GetMapping("/airports/details")
	@CircuitBreaker(name = "resourceService",fallbackMethod = "serviceAFallback")
	public String getOrders(
			@RegisteredOAuth2AuthorizedClient("users-client-oidc") OAuth2AuthorizedClient authorizedClient) {


		String jwtAccessToken = authorizedClient.getAccessToken().getTokenValue();
				System.out.println("jwtAccessToken =  " + jwtAccessToken);
				
	    String url = "http://api-gateway:8082/airports/details";
	    
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", "Bearer " + jwtAccessToken);
	    
	    HttpEntity<String> entity = new HttpEntity<>(headers);
	    ResponseEntity<String> responseEntity =
	    		restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<String>() {});

		String orders = responseEntity.getBody();
		return orders;

	}
	public String serviceAFallback(Exception e) {
		return "This is a fallback method as service is down";
	}

}
