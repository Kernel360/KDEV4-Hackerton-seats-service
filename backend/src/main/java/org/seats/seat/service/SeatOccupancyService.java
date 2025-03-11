package org.seats.seat.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.seats.seat.converter.SeatOccupancyConverter;
import org.seats.seat.domain.OccupancyListResponse;
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

    // 예약 리스트 전체 조회
    public List<OccupancyListResponse> getAllList() {
        return null;
    }

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

        // 같은 날짜와 좌석에 대해 예약이 2번을 초과했는지 확인
        checkBookingLimit(seat, startOfDay, endOfDay, requestedDate);

        SeatOccupancy occupancy = seatOccupancyConverter.toEntity(request);
        SeatOccupancy newOccupancy = seatOccupancyRepository.save(occupancy);

        // 예약 성공 시, 응답 객체로 변환하여 반환
        return seatOccupancyConverter.toResponse(newOccupancy);
    }

    // 예약이 하루에 2번 초과하는지 확인하는 메서드
    private void checkBookingLimit(Seat seat, LocalDateTime startOfDay, LocalDateTime endOfDay, LocalDate requestedDate) {
        long bookingCount = seatOccupancyRepository.countBySeatAndStartTimeBetween(seat, startOfDay, endOfDay);

        // 이미 하루에 2번 예약이 되어 있는지 체크
        if (bookingCount >= 2) {
            log.error("Attempt to book or update seat {} on {} when it is already booked twice.", seat.getId(), requestedDate);
            throw new IllegalStateException("하루에 2번 예약 가능합니다.");
        }
    }

    // 삭제하기
    public void delete(Long id) {
        SeatOccupancy seatOccupancy = seatOccupancyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("일치하는 예약이 없습니다"));

        seatOccupancyRepository.deleteById(id);
    }
}
