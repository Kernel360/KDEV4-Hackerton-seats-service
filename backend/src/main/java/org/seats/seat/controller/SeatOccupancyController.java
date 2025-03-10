package org.seats.seat.controller;

import lombok.RequiredArgsConstructor;
import org.seats.seat.domain.OccupancyRequest;
import org.seats.seat.domain.OccupancyResponse;
import org.seats.seat.repository.SeatOccupancyRepository;
import org.seats.seat.service.SeatOccupancyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seats/occupancy")
@RequiredArgsConstructor
public class SeatOccupancyController {

    private final SeatOccupancyService seatOccupancyService;
    private final SeatOccupancyRepository seatOccupancyRepository;

    // 전체 예악 현황 보기

    // 현재 예약 상황 리스트

    // 예약 하기
    @PostMapping("")
    public ResponseEntity<OccupancyResponse> createOccupancy(@RequestBody OccupancyRequest request) {
        OccupancyResponse response = seatOccupancyService.createOccupancy(request);
        return ResponseEntity.ok(response);
    }

    // 예약 수정
    @PutMapping("")
    public ResponseEntity<OccupancyResponse> updateOccupancy(@RequestBody OccupancyRequest request) {
        OccupancyResponse response = seatOccupancyService.updateOccupancy(request);
        return ResponseEntity.ok(response);
    }

    // 예악 취소
    @DeleteMapping("")
    public void delete() {
        seatOccupancyRepository.deleteAll();
    }

}
