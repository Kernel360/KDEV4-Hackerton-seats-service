package org.seats.user.controller;

import org.seats.global.intercetpor.JwtAuth;
import org.seats.user.dto.AccessTokenResponse;
import org.seats.user.entity.User;
import org.seats.user.model.UserRequest;
import org.seats.user.service.JwtService;
import org.seats.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final JwtService jwtService;

	@PostMapping("/signup")
	public AccessTokenResponse signup(
		@Valid
		@RequestBody UserRequest userRequest
	) {
		User user = userService.signup(userRequest);

		String jwtToken = jwtService.tokenCreate(user);

		return new AccessTokenResponse(jwtToken);
	}

	@PostMapping("/signin")
	public AccessTokenResponse signin(
		@Valid
		@RequestBody UserRequest userRequest
	) {
		User user = userService.signin(userRequest);

		String jwtToken = jwtService.tokenCreate(user);

		return new AccessTokenResponse(jwtToken);
	}

	@DeleteMapping("/delete")
	@JwtAuth
	public ResponseEntity<?> delete(
		@RequestAttribute("userId") Long userId
	) {
		userService.delete(userId);
		return ResponseEntity.noContent().build();
	}
}