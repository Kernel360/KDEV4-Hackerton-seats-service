package org.seats.seat.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.seats.seat.domain.OccupancyListResponse;
import org.seats.seat.domain.OccupancyRequest;
import org.seats.seat.domain.OccupancyResponse;
import org.seats.seat.repository.SeatOccupancyRepository;
import org.seats.seat.service.SeatOccupancyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    // 전체 예악 현황 보기
    
    // 현재 예약 상황 리스트

    // 예약 하기
    @PostMapping("")
    public ResponseEntity<OccupancyResponse> createOccupancy(@RequestBody OccupancyRequest request) {
        OccupancyResponse response = seatOccupancyService.createOccupancy(request);
        return ResponseEntity.ok(response);
    }

    // 예악 취소
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        seatOccupancyService.delete(id);
    }

}
