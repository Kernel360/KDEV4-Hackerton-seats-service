package org.seats.backend;

import org.junit.jupiter.api.Test;
import org.seats.user.entity.User;
import org.seats.user.service.JwtService;

public class JwtTest {

	@Test
	void parseTest() {
		JwtService jwtService = new JwtService();

		Long inputUserId = 2L;
		String accessToken = jwtService.tokenCreate(
			User.builder()
				.id(inputUserId)
				.userName("test")
				.password("test")
				.build()
		);

		Long userId = jwtService.extractId(accessToken);

		if (userId.equals(inputUserId)) {
			System.out.println("Success");
		} else {
			System.out.println("Fail");
		}
	}
}
