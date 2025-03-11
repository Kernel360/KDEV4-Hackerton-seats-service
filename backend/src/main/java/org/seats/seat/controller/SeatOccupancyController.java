package org.seats.seat.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.seats.global.intercetpor.JwtAuth;
import org.seats.seat.domain.MyOccupancyListResponse;
import org.seats.seat.domain.OccupancyListResponse;
import org.seats.seat.domain.OccupancyRequest;
import org.seats.seat.domain.OccupancyResponse;
import org.seats.seat.service.SeatOccupancyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seats/occupancy")
@RequiredArgsConstructor
public class SeatOccupancyController {

    private final SeatOccupancyService seatOccupancyService;

    // 전체 예악 현황 보기
    @GetMapping("")
    public ResponseEntity<List<OccupancyListResponse>> getAllOccupancy() {
        List<OccupancyListResponse> list = seatOccupancyService.getAllList();
        return ResponseEntity.ok(list);
    }

    // 실시간 예약 현황 보기
    @GetMapping("/now")
    public ResponseEntity<List<OccupancyListResponse>> getNowOccupancyList() {
        List<OccupancyListResponse> list = seatOccupancyService.getNowOccupanyList();
        return ResponseEntity.ok(list);
    }

    // 유저의 예약 리스트 조회
    @JwtAuth
    @GetMapping("/my")
    public ResponseEntity<List<MyOccupancyListResponse>> getMyOccupancyList(HttpServletRequest httpServletRequest) {
        List<MyOccupancyListResponse> list = seatOccupancyService.getMyOccupancyList(httpServletRequest);
        return ResponseEntity.ok(list);
    }

    // 예약 하기
    @JwtAuth
    @PostMapping("")
    public ResponseEntity<OccupancyResponse> createOccupancy(@RequestBody OccupancyRequest request, HttpServletRequest httpServletRequest) {
        OccupancyResponse response = seatOccupancyService.createOccupancy(request, httpServletRequest);
        return ResponseEntity.ok(response);
    }

    // 예악 취소
    @JwtAuth
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        seatOccupancyService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
