package com.zerobase.real_time_chat.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
			.httpBasic().disable()
			.csrf().disable()
			.cors().and()
			.authorizeRequests()
			.antMatchers("/user/register", "/user/login").permitAll() //인증필요없는 api
			.antMatchers("/**")
			.authenticated()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt를 사용하기 때문에 csrf보안을 사용할 필요x
			.and()
			.addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class)
			.build();
	}
}
