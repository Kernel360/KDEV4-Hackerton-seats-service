package org.seats.user.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.seats.seat.domain.MyOccupancyListResponse;
import org.seats.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 나의 예약 조회
    @GetMapping("/occupancy/{userId}")
    public ResponseEntity<List<MyOccupancyListResponse>> getMyOccupancyList(@PathVariable Long userId) {
        List<MyOccupancyListResponse> list = userService.getMyOccupancyList(userId);
        return ResponseEntity.ok(list);
    }

}
