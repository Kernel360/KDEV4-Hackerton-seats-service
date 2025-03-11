package org.seats.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.seats.user.entity.User;
import org.seats.user.model.UserRequest;
import org.seats.user.service.JwtService;
import org.seats.user.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/signup")
    public User signup(
            @Valid
            @RequestBody UserRequest userRequest
    ) {
        return userService.signup(userRequest);
    }

    @PostMapping("/signin")
    public String signin(
            @Valid
            @RequestBody UserRequest userRequest
    ) {
        User user = userService.signin(userRequest);

        String jwtToken = jwtService.tokenCreate(user);

        return jwtToken;
    }

    @PostMapping("/delete")
    public User delete(
            @Valid
            @RequestBody UserRequest userRequest
    ) {
        return userService.delete(userRequest);
    }

    @GetMapping("/occupancy/{userId}")
    public ResponseEntity<List<MyOccupancyListResponse>> getMyOccupancyList(@PathVariable Long userId) {
        List<MyOccupancyListResponse> list = userService.getMyOccupancyList(userId);
        return ResponseEntity.ok(list);
    }
}