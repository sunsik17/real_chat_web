package com.zerobase.real_time_chat.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private static final String TOKEN_HEADER = "Authorization";
	private static final String TOKEN_PREFIX = "Bearer ";

	private final JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String token = resolveTokenFromRequest(request);
		log.info("token : {}", token);

		//유효성 검사
		if (!StringUtils.hasText(token)) {
			log.error("authorization 을 잘못 보냈습니다.");
			filterChain.doFilter(request, response);
			return;
		}
		if (!jwtUtil.validateToken(token)) {
			log.error("token 이 만료 되었습니다.");
			filterChain.doFilter(request, response);
			return;
		}

		//토큰에서 email (PK) 꺼내기 -> api request 에 직접 넣어서 보내지 않고 토큰에서 꺼내서 넣을 예정 (유효성 검사 가능)
		String userEmail = jwtUtil.getUserEmail(token);
		log.info("userEmail:{}", userEmail);

		// 디테일 설정
		UsernamePasswordAuthenticationToken authentication =
			jwtUtil.getAuthentication(token);

		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		//권한 부여
		SecurityContextHolder
			.getContext()
			.setAuthentication(authentication);

		filterChain.doFilter(request, response);
	}

	public String resolveTokenFromRequest(HttpServletRequest request) {

		String token = request.getHeader(TOKEN_HEADER);

		if (!ObjectUtils.isEmpty(token) && token.startsWith(TOKEN_PREFIX)) {
			return token.substring(TOKEN_PREFIX.length());
		}

		return null;
	}

}


