package org.seats.user.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.seats.seat.converter.SeatOccupancyConverter;
import org.seats.seat.domain.MyOccupancyListResponse;
import org.seats.seat.entity.SeatOccupancy;
import org.seats.seat.repository.SeatOccupancyRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final SeatOccupancyRepository seatOccupancyRepository;
    private final SeatOccupancyConverter seatOccupancyConverter;

    public List<MyOccupancyListResponse> getMyOccupancyList(Long userId) {
        // 유저가 예약한 모든 예약 목록을 가져오기
        List<SeatOccupancy> occupancyList = seatOccupancyRepository.findByUserId(userId);

        // 예약 목록을 MyOccupancyListResponse로 변환하여 반환
        return occupancyList.stream()
            .map(seatOccupancyConverter::toMyOccupancyListResponse)
            .collect(Collectors.toList());
    }
}
