package org.seats.seat.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
import org.seats.user.entity.User;
import org.seats.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatOccupancyService {

    private final SeatOccupancyRepository seatOccupancyRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;
    private final SeatOccupancyConverter seatOccupancyConverter;

    // 예약 리스트 전체 조회
    public List<OccupancyListResponse> getAllList() {
        // 모든 예약 정보를 조회
        List<SeatOccupancy> seatOccupancies = seatOccupancyRepository.findAll();

        // SeatOccupancy 목록을 OccupancyListResponse 목록으로 변환
        return seatOccupancies.stream()
            .map(occupancy -> {
                String seatName = occupancy.getSeat().getName();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                String formattedStartTime = occupancy.getStartTime().format(formatter);

                // 필요한 정보를 가지고 OccupancyListResponse 생성
                return new OccupancyListResponse(
                    occupancy.getUser().getId(),          // userId
                    occupancy.getSeat().getId(),          // seatId
                    seatName,                             // seatName
                    formattedStartTime                    // startTime as string
                );
            })
            .collect(Collectors.toList());
    }

    // 예약 하기
    public OccupancyResponse createOccupancy(OccupancyRequest request) {
        // 요청된 시간대 시작 시간 파싱
        LocalDateTime startDateTime = LocalDateTime.parse(request.getStartTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDate requestedDate = startDateTime.toLocalDate();  // 요청된 날짜

        // user 확인
        Long userId = request.getUserId();
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다."));

        // 좌석 정보 조회
        Seat seat = seatRepository.findById(request.getSeatId())
            .orElseThrow(() -> new RuntimeException("Seat Not Found"));

        // 날짜 범위 설정 (하루의 시작과 끝)
        LocalDateTime startOfDay = requestedDate.atStartOfDay();  // 하루의 시작
        LocalDateTime endOfDay = requestedDate.plusDays(1).atStartOfDay();  // 하루의 끝 (다음날 00:00)

        // 로그인한 사용자가 하루에 예약 2번 하는지 확인
        checkUserBookingLimit(user, startOfDay, endOfDay);

        // 같은 시간에 이미 다른 예약이 존재하는지 확인
        checkTimeSlotAvailability(seat, startDateTime);

        SeatOccupancy occupancy = seatOccupancyConverter.toEntity(request);
        SeatOccupancy newOccupancy = seatOccupancyRepository.save(occupancy);

        // 예약 성공 시, 응답 객체로 변환하여 반환
        return seatOccupancyConverter.toResponse(newOccupancy);
    }

    // 예약이 하루에 2번 초과하는지 확인하는 메서드 (조건 1)
    private void checkUserBookingLimit(User user, LocalDateTime startOfDay, LocalDateTime endOfDay) {
        long userBookingCount = seatOccupancyRepository.countByUserAndStartTimeBetween(user, startOfDay, endOfDay);

        // 사용자가 하루에 2번 예약을 시도하는지 체크
        if (userBookingCount >= 2) {
            log.error("User {} has already booked twice on this day.", user.getId());
            throw new IllegalStateException("하루에 2번 예약 가능합니다.");
        }
    }

    // 동일한 시간에 다른 사용자가 이미 예약한 경우를 체크하는 메서드 (조건 2)
    private void checkTimeSlotAvailability(Seat seat, LocalDateTime startDateTime) {
        long conflictingBookings = seatOccupancyRepository.countBySeatAndStartTime(seat, startDateTime);

        // 동일한 시간에 두 명 이상 예약할 수 없도록 체크
        if (conflictingBookings > 0) {
            log.error("The seat {} is already booked at {}.", seat.getId(), startDateTime);
            throw new IllegalStateException("다른 사용자들이 이미 같은 시간에 예약을 했습니다.");
        }
    }

    // 삭제하기
    public void delete(Long id) {
        SeatOccupancy seatOccupancy = seatOccupancyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("일치하는 예약이 없습니다"));

        seatOccupancyRepository.deleteById(id);
    }
}
