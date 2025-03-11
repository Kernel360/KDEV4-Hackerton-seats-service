package org.seats.seat.converter;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.extern.slf4j.Slf4j;
import org.seats.seat.domain.MyOccupancyListResponse;
import org.seats.seat.domain.OccupancyRequest;
import org.seats.seat.domain.OccupancyResponse;
import org.seats.seat.entity.Seat;
import org.seats.seat.entity.SeatOccupancy;
import org.seats.seat.repository.SeatRepository;
import org.seats.user.entity.User;
import org.seats.user.entity.UserRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Slf4j
@Component
public class SeatOccupancyConverter {

	private final UserRepository userRepository;
	private final SeatRepository seatRepository;

	public SeatOccupancy toEntity(OccupancyRequest request, Long userId) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		// userId를 통해 User 객체를 조회
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("User Not Found"));

		// 좌석 정보 조회
		Seat seat = seatRepository.findById(request.getSeatId())
			.orElseThrow(() -> new RuntimeException("Seat Not Found"));

		return SeatOccupancy.builder()
			.user(user)
			.seat(seat)
			.startTime(LocalDateTime.parse(request.getStartTime(), formatter))
			.build();
	}

	// 예약 응답 객체 변환
	public OccupancyResponse toResponse(SeatOccupancy seatOccupancy) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		return OccupancyResponse.builder()
			.userId(seatOccupancy.getUser().getId())
			.seatId(seatOccupancy.getSeat().getId())
			.startTime(seatOccupancy.getStartTime().format(formatter))
			.build();
	}

	// 예약 목록 응답 객체 변환
	public MyOccupancyListResponse toMyOccupancyListResponse(SeatOccupancy seatOccupancy) {
		return MyOccupancyListResponse.builder()
			.seatId(seatOccupancy.getSeat().getId())
			.seatName(seatOccupancy.getSeat().getName()) // 좌석 이름
			.startTime(seatOccupancy.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))) // 시작 시간 포맷
			.build();
	}
}