package org.seats.seat.controller;

import java.time.LocalDate;
import java.util.List;

import org.seats.global.intercetpor.JwtAuth;
import org.seats.seat.domain.DeleteRequest;
import org.seats.seat.domain.MyOccupancyListResponse;
import org.seats.seat.domain.OccupancyListResponse;
import org.seats.seat.domain.OccupancyRequest;
import org.seats.seat.domain.OccupancyResponse;
import org.seats.seat.domain.OccupancyTableResponse;
import org.seats.seat.service.SeatOccupancyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/seats/occupancy")
@RequiredArgsConstructor
public class SeatOccupancyController {

	private final SeatOccupancyService seatOccupancyService;

	// 전체 예악 현황 보기
	@GetMapping("")
	public ResponseEntity<OccupancyTableResponse> getAllOccupancy(String date) {
		LocalDate formattedDate;
		if (date == null || date.isEmpty()) {
			formattedDate = LocalDate.now();
		} else {
			formattedDate = LocalDate.parse(date);
		}

		var response = seatOccupancyService.getOccupancyTable(formattedDate);
		return ResponseEntity.ok(response);
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
	public ResponseEntity<List<MyOccupancyListResponse>> getMyOccupancyList(@RequestAttribute("userId") Long userId) {
		List<MyOccupancyListResponse> list = seatOccupancyService.getMyOccupancyList(userId);
		return ResponseEntity.ok(list);
	}

	// 예약 하기
	@JwtAuth
	@PostMapping("")
	public ResponseEntity<OccupancyResponse> createOccupancy(@RequestBody OccupancyRequest request,
		@RequestAttribute("userId") Long userId) {
		OccupancyResponse response = seatOccupancyService.createOccupancy(request, userId);
		return ResponseEntity.ok(response);
	}

	// 예악 취소
	@JwtAuth
	@DeleteMapping("/")
	public ResponseEntity<?> delete(@RequestBody DeleteRequest request, @RequestAttribute("userId") Long userId) {
		seatOccupancyService.delete(request.getSeatId(), LocalDate.parse(request.getStartTime()), userId);
		return ResponseEntity.noContent().build();
	}

}
