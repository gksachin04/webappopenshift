package com.appsdeveloperblog.photoapp.oauthserver;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SpringSecurityConfiguration {

	@Bean
	SecurityFilterChain configureSecurityFilterChain(HttpSecurity http) throws Exception {
		
		http
		.authorizeHttpRequests(authorizeRequests -> {
			try {
				authorizeRequests.antMatchers("/v3/api-docs/**").permitAll()
						.and().authorizeRequests().anyRequest().authenticated();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		})
		.formLogin(Customizer.withDefaults());
		
		return http.build();
		
	}
	

	@Bean
	public UserDetailsService users() {
		
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

		manager.createUser(User
				.withUsername("developer1")
				.password(encoder.encode("password1"))
				.roles("ADMIN")
				.build());
		manager.createUser(User
				.withUsername("developer2")
				.password(encoder.encode("password2"))
				.roles("USER")
				.build());

		return manager;
		
	}
	
}
