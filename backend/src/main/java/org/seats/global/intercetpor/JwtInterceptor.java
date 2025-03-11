package org.seats.global.intercetpor;

import java.io.IOException;

import org.seats.user.service.JwtService;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

	private final JwtService jwtService;

	@Override
	public boolean preHandle(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler
	) throws Exception {
		if (!(handler instanceof HandlerMethod handlerMethod)) {
			return true;
		}

		JwtAuth jwtAuth = handlerMethod.getMethodAnnotation(JwtAuth.class);
		if (jwtAuth == null) {
			return true;
		}

		return extractJwtToken(request, response);
	}

	private boolean extractJwtToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String token = extractHeader(request);

		Long id = jwtService.extractId(token);
		request.setAttribute("userId", id);
		return true;
	}

	private String extractHeader(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");

		if (authHeader == null || authHeader.isEmpty()) {
			return null;
		}
		if (!authHeader.startsWith("Bearer ")) {
			return null;
		}

		return authHeader.substring(7);
	}
}