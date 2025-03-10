package org.seats.seat.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.seats.seat.converter.SeatOccupancyConverter;
import org.seats.seat.domain.OccupancyRequest;
import org.seats.seat.domain.OccupancyResponse;
import org.seats.seat.entity.Seat;
import org.seats.seat.entity.SeatOccupancy;
import org.seats.seat.repository.SeatOccupancyRepository;
import org.seats.seat.repository.SeatRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatOccupancyService {

    private final SeatOccupancyRepository seatOccupancyRepository;
    private final SeatRepository seatRepository;
    private final SeatOccupancyConverter seatOccupancyConverter;

    // 예약 하기
    public OccupancyResponse createOccupancy(OccupancyRequest request) {
        // 요청된 시간대 시작 시간 파싱
        LocalDateTime startDateTime = LocalDateTime.parse(request.getStartTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDate requestedDate = startDateTime.toLocalDate();  // 요청된 날짜

        // 좌석 정보 조회
        Seat seat = seatRepository.findById(request.getSeatId())
            .orElseThrow(() -> new RuntimeException("Seat Not Found"));

        // 날짜 범위 설정 (하루의 시작과 끝)
        LocalDateTime startOfDay = requestedDate.atStartOfDay();  // 하루의 시작
        LocalDateTime endOfDay = requestedDate.plusDays(1).atStartOfDay();  // 하루의 끝 (다음날 00:00)

        log.info("Checking availability for seat: {} from {} to {}", seat.getId(), startOfDay, endOfDay);

        // 같은 날짜와 좌석에 대해 예약이 2번을 초과했는지 확인
        long bookingCount = seatOccupancyRepository.countBySeatAndStartTimeBetween(seat, startOfDay, endOfDay);

        // 이미 하루에 2번 예약이 되어 있는지 체크
        if (bookingCount >= 2) {
            log.error("Attempt to book seat {} on {} when it is already booked twice.", seat.getId(), requestedDate);
            throw new IllegalStateException("하루에 2번 예약 가능합니다.");
        }

        SeatOccupancy occupancy = seatOccupancyConverter.toEntity(request);
        SeatOccupancy newOccupancy = seatOccupancyRepository.save(occupancy);

        // 예약 성공 시, 응답 객체로 변환하여 반환
        return seatOccupancyConverter.toResponse(newOccupancy);

    }


    // 예약 정보 수정하기
    public OccupancyResponse updateOccupancy(OccupancyRequest request) {
        return null;
    }
}
